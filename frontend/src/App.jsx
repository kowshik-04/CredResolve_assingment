import { useContext } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import { UserContext } from "./context/UserContext";
import SelectUser from "./pages/SelectUser";
import Groups from "./pages/Groups";
import GroupDetails from "./pages/GroupDetails";
import CreateGroup from "./pages/CreateGroup";

export default function App() {
  const context = useContext(UserContext);

  // 🔴 VERY IMPORTANT GUARD
  if (!context) {
    return <h2>Context not ready</h2>;
  }

  const { user } = context;

  if (!user) return <SelectUser />;

  return (
    <Routes>
      <Route path="/" element={<Groups />} />
      <Route path="/groups/:id" element={<GroupDetails />} />
      <Route path="/create-group" element={<CreateGroup />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
