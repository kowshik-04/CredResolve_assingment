export default function SettlementCard({ s }) {
  return (
    <div className="bg-yellow-50 p-3 rounded-lg shadow">
      <p className="font-medium">
        User {s.fromUserId} ➜ User {s.toUserId}
      </p>
      <p className="text-lg font-bold">₹ {s.amount}</p>
    </div>
  );
}
