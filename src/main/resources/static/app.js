const API_BASE_URL = "http://localhost:5000";

let loggedInUser = localStorage.getItem("cloudstore_user");

let productsVisible = false;
let ordersVisible = false;
let cart = [];

updateUserUI();

async function registerUser() {
    const username = document.getElementById("registerName").value;
    const password = document.getElementById("registerPassword").value;

    console.log(username, password);

    const response = await fetch(`${API_BASE_URL}/api/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    const data = await response.json();

    if (!response.ok) {
        alert("Registreringen misslyckades");
        return;
    }

    loggedInUser = data.username;
    localStorage.setItem("cloudstore_user", loggedInUser);

    updateUserUI();

    alert("Registrerad");
}

async function loginUser() {
    const username = document.getElementById("loginUsername").value;
    const password = document.getElementById("loginPassword").value;

    console.log(username, password);

    const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    const data = await response.json();

    if (!response.ok) {
        alert("Inloggningen misslyckades");
        return;
    }

    loggedInUser = data.username;
    localStorage.setItem("cloudstore_user", loggedInUser);

    updateUserUI();

    alert("Inloggad");
}

async function loadProducts() {


    if (productsVisible) {
        document.getElementById("products").innerHTML = "";
        productsVisible = false;
        return;
    }

    const response = await fetch(`${API_BASE_URL}/api/products`);

    if (!response.ok) {
        alert("Kunde inte hämta produkter");
        return;
    }

    const products = await response.json();

    const productDiv = document.getElementById("products");
    productDiv.innerHTML = "";

    products.forEach(product => {
        const div = document.createElement("div");
        div.className = "product";

        div.innerHTML = `
            <h3>${product.title}</h3>
            <img src="${product.image}" alt="${product.title}" width="150">
            <p>${product.description}</p>
            <p><strong>Pris:</strong> ${product.price}</p>
            <button onclick="addToCart(${product.id})">
             Lägg i varukorg
             </button>
        `;

        productDiv.appendChild(div);
    });

    productsVisible = true;
}

async function createOrder(productId) {


    if (!loggedInUser) {
        alert("Du måste logga in först");
        return;
    }

    const response = await fetch(`${API_BASE_URL}/api/orders`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",

        },
        body: JSON.stringify({
            username: loggedInUser,
            items: [
                {
                    productId: productId,
                    quantity: 3
                }
            ]
        })
    });

    if (!response.ok) {
        alert("Kunde inte skapa order");
        return;
    }

    alert("Order skapad");
}

async function loadOrders() {

    if (ordersVisible) {
        document.getElementById("orders").innerHTML = "";
        ordersVisible = false;
        return;
    }

    if (!loggedInUser) {
        alert("Du måste logga in först");
        return;
    }

    const response = await fetch(`${API_BASE_URL}/api/orders?username=${loggedInUser}`);

    if (!response.ok) {
        alert("Kunde inte hämta ordrar");
        return;
    }

    const orders = await response.json();

    const orderDiv = document.getElementById("orders");
    orderDiv.innerHTML = "";

    orders.forEach(order => {
        const div = document.createElement("div");
        div.className = "message";

        const itemsHtml = (order.items || [])
            .map(item =>
                `${item.productTitle} x ${item.quantity} (${item.priceAtPurchase} kr)`
            )
            .join("<br>");

        div.innerHTML = `
            <p><strong>Order-ID:</strong> ${order.orderId}</p>
            <p><strong>Användare:</strong> ${order.username}</p>
            <p><strong>Totalpris:</strong> ${order.totalPrice} kr</p>
            <p><strong>Skapad:</strong> ${order.createdAt}</p>
            <p><strong>Produkter:</strong><br>${itemsHtml}</p>
        `;

        orderDiv.appendChild(div);
    });

    ordersVisible = true;


}

function logoutUser() {


    loggedInUser = null;
    localStorage.removeItem("cloudstore_user");

    document.getElementById("orders").innerHTML = "";
    document.getElementById("products").innerHTML = "";

    updateUserUI();

    alert("Du är nu utloggad");
}

function updateUserUI() {
    const user = localStorage.getItem("cloudstore_user");

    loggedInUser = user;

    if (user) {
        document.getElementById("currentUser").innerText = `Inloggad som: ${user}`;
    } else {
        document.getElementById("currentUser").innerText = "Inte inloggad";
    }
}

function addToCart(productId) {
    if (!loggedInUser) {
        alert("Du måste logga in först");
        return;
    }

    const existingItem = cart.find(i => i.productId === productId);

    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({
            productId: productId,
            quantity: 1
        });
    }

    console.log("Cart:", cart);
    alert("Tillagd i varukorg");
}

async function placeOrder() {
    if (!loggedInUser) {
        alert("Du måste logga in först");
        return;
    }

    if (cart.length === 0) {
        alert("Varukorgen är tom");
        return;
    }

    const response = await fetch(`${API_BASE_URL}/api/orders`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: loggedInUser,
            items: cart
        })
    });

    if (!response.ok) {
        alert("Kunde inte skapa order");
        return;
    }

    alert("Order skapad!");

    // töm cart efter köp
    cart = [];
}

function showView(id) {
    const el = document.getElementById(id);

    // om den redan är synlig → dölj den
    if (el.classList.contains("active")) {
        el.classList.remove("active");
        return;
    }

    // annars dölj alla
    document.querySelectorAll(".view").forEach(v => {
        v.classList.remove("active");
    });

    // visa vald
    el.classList.add("active");

    if (id === "ordersSection") {
        loadOrders();
    }

}

window.onload = () => {
    loadProducts();
};

updateUserUI();

