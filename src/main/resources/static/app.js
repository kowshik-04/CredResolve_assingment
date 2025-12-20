const API = "http://localhost:8080";

let currentUserId = null;
let currentGroupId = null;

// ---------- USERS ----------
async function loadUsers() {
  const res = await fetch(`${API}/users`);
  const users = await res.json();

  const select = document.getElementById("userSelect");
  select.innerHTML = "";

  users.forEach(u => {
    const opt = document.createElement("option");
    opt.value = u.id;
    opt.textContent = u.name;
    select.appendChild(opt);
  });

  currentUserId = users[0]?.id;
  select.onchange = () => {
    currentUserId = select.value;
    loadGroups();
  };

  loadGroups();
}

// ---------- GROUPS ----------
async function loadGroups() {
  const res = await fetch(`${API}/groups/user/${currentUserId}`);
  const groups = await res.json();

  const ul = document.getElementById("groupsList");
  ul.innerHTML = "";

  groups.forEach(g => {
    const li = document.createElement("li");
    li.textContent = g.name;
    li.onclick = () => selectGroup(g.id);
    ul.appendChild(li);
  });
}

async function createGroup() {
  const name = document.getElementById("newGroupName").value;
  await fetch(`${API}/groups`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, createdById: currentUserId })
  });
  loadGroups();
}

// ---------- GROUP DETAILS ----------
async function selectGroup(groupId) {
  currentGroupId = groupId;
  loadMembers();
  loadExpenses();
  loadBalances();
  loadSettlements();
}

async function loadMembers() {
  const res = await fetch(`${API}/groups/${currentGroupId}/members`);
  const data = await res.json();
  const ul = document.getElementById("membersList");
  ul.innerHTML = data.map(u => `<li>${u.name}</li>`).join("");
}

async function loadExpenses() {
  const res = await fetch(`${API}/groups/${currentGroupId}/expenses`);
  const data = await res.json();
  const ul = document.getElementById("expensesList");
  ul.innerHTML = data.map(e =>
    `<li>${e.description} - ₹${e.totalAmount}</li>`
  ).join("");
}

// ---------- EXPENSE ----------
async function addExpense() {
  await fetch(`${API}/expenses`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      description: desc.value,
      amount: amount.value,
      splitType: splitType.value,
      paidById: currentUserId,
      groupId: currentGroupId,
      splitDetails: {}
    })
  });
  loadExpenses();
  loadBalances();
  loadSettlements();
}

// ---------- BALANCES ----------
async function loadBalances() {
  const res = await fetch(`${API}/balances/${currentGroupId}`);
  const data = await res.json();
  const ul = document.getElementById("balancesList");
  ul.innerHTML = data.map(b =>
    `<li>${b.user.name}: ₹${b.amount}</li>`
  ).join("");
}

// ---------- SETTLEMENT ----------
async function loadSettlements() {
  const res = await fetch(`${API}/settlements/${currentGroupId}`);
  const data = await res.json();
  const ul = document.getElementById("settlementsList");

  ul.innerHTML = data.map(s =>
    `<li>User ${s.fromUserId} → User ${s.toUserId} ₹${s.amount}</li>`
  ).join("");
}

loadUsers();
