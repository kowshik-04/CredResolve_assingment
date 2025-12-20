import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getBalances } from "../api/balances.api";
import { getSettlements, settleUp } from "../api/settlements.api";
import { toRupees } from "../utils/money";

export default function GroupDetails() {
  const { id } = useParams();
  const [balances, setBalances] = useState([]);
  const [settlements, setSettlements] = useState([]);

  useEffect(() => {
    getBalances(id).then(setBalances);
    getSettlements(id).then(setSettlements);
  }, [id]);

  return (
    <div className="container">
      <h2>Group Details</h2>

      <h3>Balances</h3>
      {balances.map(b => (
        <div key={b.user.id}>
          {b.user.name}: ₹{toRupees(b.amountPaise)}
        </div>
      ))}

      <h3>Settle</h3>
      {settlements.map((s, i) => (
        <div key={i}>
          User {s.fromUserId} → User {s.toUserId} : ₹{s.amount}
        </div>
      ))}
    </div>
  );
}
