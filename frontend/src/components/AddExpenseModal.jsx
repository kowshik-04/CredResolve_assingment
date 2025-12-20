import { useState, useContext } from "react";
import { addExpense } from "../api/expenses.api";
import { UserContext } from "../context/UserContext";
import { toPaise } from "../utils/money";

export default function AddExpenseModal({ groupId, onClose }) {
  const { user } = useContext(UserContext);

  const [desc, setDesc] = useState("");
  const [amount, setAmount] = useState("");
  const [type, setType] = useState("EQUAL");

  const submit = async () => {
    await addExpense({
      description: desc,
      amountPaise: toPaise(amount),
      splitType: type,
      paidById: user.id,
      groupId
    });
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center">
      <div className="bg-white p-6 rounded-xl w-96 space-y-4">
        <h2 className="text-xl font-bold">Add Expense</h2>

        <input
          className="input"
          placeholder="Description"
          onChange={(e) => setDesc(e.target.value)}
        />

        <input
          className="input"
          placeholder="Amount (₹)"
          type="number"
          onChange={(e) => setAmount(e.target.value)}
        />

        <select
          className="input"
          onChange={(e) => setType(e.target.value)}
        >
          <option value="EQUAL">Equal</option>
          <option value="EXACT">Exact</option>
          <option value="PERCENTAGE">Percentage</option>
        </select>

        <div className="flex justify-end gap-3">
          <button onClick={onClose}>Cancel</button>
          <button className="btn-primary" onClick={submit}>
            Save
          </button>
        </div>
      </div>
    </div>
  );
}
