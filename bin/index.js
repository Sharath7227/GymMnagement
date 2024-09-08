document.getElementById('registerForm')?.addEventListener('submit', async function (e) {
    e.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, email, password })
        });

        const result = await response.text(); // Text response from server

        if (response.ok) {
            alert(result);
            window.location.href = 'login.html';
        } else {
            alert(result);
        }
    } catch (error) {
        console.error('Error:', error);
    }
});
