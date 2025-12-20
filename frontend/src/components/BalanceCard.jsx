import { toRupees } from "../utils/money";

export default function BalanceCard({ balance }) {
  return (
    <div
      className={`p-4 rounded-xl shadow ${
        balance.amountPaise >= 0
          ? "bg-green-50 border-green-400"
          : "bg-red-50 border-red-400"
      }`}
    >
      <h4 className="font-semibold">{balance.user.name}</h4>
      <p className="text-xl">
        ₹ {toRupees(Math.abs(balance.amountPaise))}
      </p>
    </div>
  );
}
