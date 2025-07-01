<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fruit Service Engine</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            color: #444;
            margin-top: 40px;
        }

        form {
            background: #fff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 400px;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input, select {
            padding: 8px;
            width: 100%;
            box-sizing: border-box;
        }

        /* Ensure dropdown text is visible */
        select option {
            color: #000;
            background-color: #fff;
        }

        .field {
            display: none; /* Hide all fields by default */
        }

        .visible {
            display: block; /* Reveal fields based on action */
        }

        .submit-btn {
            margin-top: 20px;
            background: #007bff;
            color: #fff;
            border: none;
            padding: 10px;
            cursor: pointer;
            border-radius: 5px;
        }

        .submit-btn:hover {
            background: #0056b3;
        }

        .result-box {
            background: #fff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
            width: 400px;
            white-space: pre-wrap;
        }
    </style>

    <script>
        // Dynamically populate fruit list in dropdown (used in update/delete/cost actions)
        function fetchFruitListAndPopulate() {
            fetch("FruitListServlet")
                .then(response => response.json())
                .then(fruits => {
                    console.log("Fetched fruits:", fruits);

                    const select = document.createElement("select");
                    select.name = "name";
                    select.required = true;

                    fruits
                        .filter(f => f && f.trim() !== "")
                        .sort()
                        .forEach(fruit => {
                            const opt = document.createElement("option");
                            opt.value = fruit;
                            opt.textContent = fruit;
                            select.appendChild(opt);
                        });

                    const nameField = document.getElementById("name-field");
                    nameField.innerHTML = "<label>Fruit Name:</label>";
                    nameField.appendChild(select);
                    nameField.classList.add("visible");
                })
                .catch(err => {
                    console.error("Failed to fetch fruit list", err);
                });
        }

        // Show/hide fields based on selected task
        function updateFields() {
            const action = document.getElementById("action").value;

            const fields = {
                name: ["add", "update", "delete", "cost"],
                price: ["add", "update", "receipt"],
                quantity: ["cost"],
                amount: ["receipt"],
                cashier: ["receipt"]
            };

            document.querySelectorAll(".field").forEach(el => el.classList.remove("visible"));

            for (const [id, actions] of Object.entries(fields)) {
                if (actions.includes(action)) {
                    document.getElementById(id + "-field").classList.add("visible");
                }
            }

            const nameField = document.getElementById("name-field");

            if (["update", "delete", "cost"].includes(action)) {
                fetchFruitListAndPopulate();
            } else if (action === "add") {
                nameField.classList.add("visible");
                nameField.innerHTML = `
                    <label>Fruit Name:</label>
                    <input type="text" name="name" required />
                `;
            } else {
                nameField.classList.remove("visible");
                nameField.innerHTML = ""; // Clear name field if not needed
            }
        }

        window.onload = updateFields;
    </script>
</head>
<body>

<h1>🍎 Fruit Service</h1>

<!-- Main Form: Submits to FruitServlet -->
<form action="FruitServlet" method="post">
    <label for="action">Task:</label>
    <select id="action" name="action" onchange="updateFields()">
        <option value="add">Add Fruit</option>
        <option value="update">Update Fruit</option>
        <option value="delete">Delete Fruit</option>
        <option value="cost">Calculate Cost</option>
        <option value="receipt">Print Receipt</option>
        <option value="list">List Fruits</option>
    </select>

    <!-- Dynamic Fields -->
    <div id="name-field" class="field">
        <label>Fruit Name:</label>
        <input type="text" name="name"/>
    </div>

    <div id="price-field" class="field">
        <label>Price / Total Cost:</label>
        <input type="text" name="price"/>
    </div>

    <div id="quantity-field" class="field">
        <label>Quantity:</label>
        <input type="text" name="quantity"/>
    </div>

    <div id="amount-field" class="field">
        <label>Amount Given:</label>
        <input type="text" name="amount"/>
    </div>

    <div id="cashier-field" class="field">
        <label>Cashier Name:</label>
        <input type="text" name="cashier"/>
    </div>

    <input type="submit" value="Submit" class="submit-btn"/>
</form>

<!-- Display result returned from FruitServlet -->
<jsp:include page="result.jsp" flush="true"/>
</body>
</html>
