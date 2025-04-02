const apiBase = 'http://localhost:8069/api';


async function fetchCategories() {
    try {
        const response = await fetch(apiBase + '/categories');
        const categories = await response.json();


        const categoriesTableBody = document.querySelector('#categoriesTable tbody');
        categoriesTableBody.innerHTML = '';
        categories.forEach(cat => {
            const row = document.createElement('tr');
            row.innerHTML = `
                        <td>${cat.id}</td>
                        <td>${cat.name}</td>
                        <td>${cat.description}</td>
                        <td class="actions">
                        <button onclick="editCategory(${cat.id})">Edit</button>
                        <button onclick="deleteCategory(${cat.id})">Delete</button>
                        </td>
                    `;
            categoriesTableBody.appendChild(row);
        });


        const filterCategory = document.getElementById('filterCategory');
        const expenseCategory = document.getElementById('expenseCategory');
        filterCategory.innerHTML = '<option value="">All</option>';
        expenseCategory.innerHTML = '';
        categories.forEach(cat => {
            const option1 = document.createElement('option');
            option1.value = cat.id;
            option1.textContent = cat.name;
            filterCategory.appendChild(option1);

            const option2 = document.createElement('option');
            option2.value = cat.id;
            option2.textContent = cat.name;
            expenseCategory.appendChild(option2);
        });
    } catch (error) {
        console.error('Error fetching categories:', error);
    }
}


async function fetchExpenses() {
    try {
        let url = apiBase + '/expenses?';
        const sort = document.getElementById('sortOrder').value;
        const categoryId = document.getElementById('filterCategory').value;
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        if (sort) url += 'sort=' + sort + '&';
        if (categoryId) url += 'categoryId=' + categoryId + '&';
        if (startDate) url += 'startDate=' + startDate + '&';
        if (endDate) url += 'endDate=' + endDate + '&';

        const response = await fetch(url);
        const expenses = await response.json();

        const expensesTableBody = document.querySelector('#expensesTable tbody');
        expensesTableBody.innerHTML = '';
        expenses.forEach(exp => {
            const row = document.createElement('tr');
            row.innerHTML = `
            <td>${exp.id}</td>
            <td>${exp.description}</td>
            <td>${exp.amount}</td>
            <td>${new Date(exp.date).toLocaleDateString()}</td>
            <td>${exp.category ? exp.category.name : ''}</td>
            <td class="actions">
              <button onclick="editExpense(${exp.id})">Edit</button>
              <button onclick="deleteExpense(${exp.id})">Delete</button>
            </td>
          `;
            expensesTableBody.appendChild(row);
        });
    } catch (error) {
        console.error('Error fetching expenses:', error);
    }
}


document.getElementById('categoryForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    const id = document.getElementById('categoryId').value;
    const name = document.getElementById('categoryName').value;
    const description = document.getElementById('categoryDescription').value;
    const payload = { name, description };

    try {
        let response;
        if (id) {
            response = await fetch(apiBase + '/categories/' + id, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
        } else {
            response = await fetch(apiBase + '/categories', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
        }
        if (response.ok) {
            fetchCategories();
            document.getElementById('categoryForm').reset();
        }
    } catch (error) {
        console.error('Error saving category:', error);
    }
});


document.getElementById('expenseForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    const id = document.getElementById('expenseId').value;
    const description = document.getElementById('expenseDescription').value;
    const amount = parseFloat(document.getElementById('expenseAmount').value);
    const date = document.getElementById('expenseDate').value;
    const categoryId = document.getElementById('expenseCategory').value;
    const payload = { description, amount, date, category: { id: categoryId } };

    try {
        let response;
        if (id) {
            response = await fetch(apiBase + '/expenses/' + id, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
        } else {
            response = await fetch(apiBase + '/expenses', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
        }
        if (response.ok) {
            fetchExpenses();
            document.getElementById('expenseForm').reset();
        }
    } catch (error) {
        console.error('Error saving expense:', error);
    }
});


async function editCategory(id) {
    try {
        const response = await fetch(apiBase + '/categories/id/' + id);
        const cat = await response.json();
        document.getElementById('categoryId').value = cat.id;
        document.getElementById('categoryName').value = cat.name;
        document.getElementById('categoryDescription').value = cat.description;
    } catch (error) {
        console.error('Error fetching category:', error);
    }
}


async function deleteCategory(id) {
    if (confirm('Are you sure you want to delete this category?')) {
        try {
            const response = await fetch(apiBase + '/categories/' + id, { method: 'DELETE' });
            if (response.ok) {
                fetchCategories();
            } else {
                alert('Cannot delete category "Uncategorized" or a category in use.');
            }
        } catch (error) {
            console.error('Error deleting category:', error);
        }
    }
}


async function editExpense(id) {
    try {
        const response = await fetch(apiBase + '/expenses/' + id);
        const exp = await response.json();
        document.getElementById('expenseId').value = exp.id;
        document.getElementById('expenseDescription').value = exp.description;
        document.getElementById('expenseAmount').value = exp.amount;

        document.getElementById('expenseDate').value = exp.date.split('T')[0];
        if (exp.category) {
            document.getElementById('expenseCategory').value = exp.category.id;
        }
    } catch (error) {
        console.error('Error fetching expense:', error);
    }
}


async function deleteExpense(id) {
    if (confirm('Are you sure you want to delete this expense?')) {
        try {
            const response = await fetch(apiBase + '/expenses/' + id, { method: 'DELETE' });
            if (response.ok) {
                fetchExpenses();
            }
        } catch (error) {
            console.error('Error deleting expense:', error);
        }
    }
}


document.getElementById('filterBtn').addEventListener('click', function () {
    fetchExpenses();
});


fetchCategories();
fetchExpenses();