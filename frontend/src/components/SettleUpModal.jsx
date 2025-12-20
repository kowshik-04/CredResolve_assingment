import { useState } from "react";
import { settleUp } from "../api/settlements.api";

export default function SettleUpModal({ settlement, groupId, onClose }) {
  const [loading, setLoading] = useState(false);

  const confirm = async () => {
    setLoading(true);
    await settleUp({
      groupId,
      fromUserId: settlement.fromUserId,
      toUserId: settlement.toUserId,
      amountPaise: settlement.amountPaise
    });
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center">
      <div className="bg-white p-6 rounded-xl w-96 space-y-4">
        <h2 className="text-xl font-bold">Settle Up</h2>
        <p>
          User {settlement.fromUserId} pays User {settlement.toUserId}
        </p>
        <p className="text-lg font-semibold">
          ₹ {settlement.amount}
        </p>

        <div className="flex justify-end gap-3">
          <button onClick={onClose}>Cancel</button>
          <button
            disabled={loading}
            className="btn-primary"
            onClick={confirm}
          >
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
}
