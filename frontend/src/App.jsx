import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import ProtectedRoute from './components/ProtectedRoute'
import Layout from './components/Layout'
import Login from './pages/Login'
import Register from './pages/Register'
import Dashboard from './pages/Dashboard'
import Habits from './pages/Habits'
import Entries from './pages/Entries'
import Milestones from './pages/Milestones'

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route path="/" element={<ProtectedRoute><Layout /></ProtectedRoute>}>
            <Route index element={<Navigate to="/dashboard" replace />} />
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="habits" element={<Habits />} />
            <Route path="entries" element={<Entries />} />
            <Route path="milestones" element={<Milestones />} />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  )
}

export default App
