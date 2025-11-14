import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { habitAPI, entryAPI, milestoneAPI } from '../services/api'

const Dashboard = () => {
  const [stats, setStats] = useState({
    totalHabits: 0,
    totalEntries: 0,
    totalMilestones: 0,
    completedMilestones: 0,
  })
  const [recentHabits, setRecentHabits] = useState([])
  const [recentEntries, setRecentEntries] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchDashboardData()
  }, [])

  const fetchDashboardData = async () => {
    try {
      const [habitsRes, entriesRes, milestonesRes] = await Promise.all([
        habitAPI.getAll(),
        entryAPI.getAll(),
        milestoneAPI.getAll(),
      ])

      const habits = habitsRes.data
      const entries = entriesRes.data
      const milestones = milestonesRes.data

      setStats({
        totalHabits: habits.length,
        totalEntries: entries.length,
        totalMilestones: milestones.length,
        completedMilestones: milestones.filter(m => m.status === 'COMPLETED').length,
      })

      setRecentHabits(habits.slice(0, 5))
      setRecentEntries(entries.slice(0, 5))
    } catch (error) {
      console.error('Error fetching dashboard data:', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    )
  }

  return (
    <div className="px-4 sm:px-0">
      <h1 className="text-3xl font-bold text-gray-900 mb-8">Dashboard</h1>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-primary-500 rounded-md p-3">
                <svg
                  className="h-6 w-6 text-white"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"
                  />
                </svg>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 truncate">Total Habits</dt>
                  <dd className="text-lg font-semibold text-gray-900">{stats.totalHabits}</dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-green-500 rounded-md p-3">
                <svg
                  className="h-6 w-6 text-white"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M12 6v6m0 0v6m0-6h6m-6 0H6"
                  />
                </svg>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 truncate">Total Entries</dt>
                  <dd className="text-lg font-semibold text-gray-900">{stats.totalEntries}</dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-yellow-500 rounded-md p-3">
                <svg
                  className="h-6 w-6 text-white"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M13 10V3L4 14h7v7l9-11h-7z"
                  />
                </svg>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 truncate">Total Milestones</dt>
                  <dd className="text-lg font-semibold text-gray-900">{stats.totalMilestones}</dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-purple-500 rounded-md p-3">
                <svg
                  className="h-6 w-6 text-white"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                  />
                </svg>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 truncate">
                    Completed Milestones
                  </dt>
                  <dd className="text-lg font-semibold text-gray-900">
                    {stats.completedMilestones}
                  </dd>
                </dl>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Recent Activity */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Recent Habits */}
        <div className="bg-white shadow rounded-lg p-6">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-xl font-semibold text-gray-900">Recent Habits</h2>
            <Link
              to="/habits"
              className="text-sm text-primary-600 hover:text-primary-500 font-medium"
            >
              View all
            </Link>
          </div>
          {recentHabits.length === 0 ? (
            <p className="text-gray-500 text-center py-8">No habits yet. Create your first habit!</p>
          ) : (
            <ul className="divide-y divide-gray-200">
              {recentHabits.map((habit) => (
                <li key={habit.id} className="py-3">
                  <div className="flex justify-between">
                    <div>
                      <p className="font-medium text-gray-900">{habit.name}</p>
                      <p className="text-sm text-gray-500">{habit.description}</p>
                    </div>
                    <span className="text-sm text-gray-500">{habit.frequency}</span>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* Recent Entries */}
        <div className="bg-white shadow rounded-lg p-6">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-xl font-semibold text-gray-900">Recent Entries</h2>
            <Link
              to="/entries"
              className="text-sm text-primary-600 hover:text-primary-500 font-medium"
            >
              View all
            </Link>
          </div>
          {recentEntries.length === 0 ? (
            <p className="text-gray-500 text-center py-8">No entries yet. Log your first entry!</p>
          ) : (
            <ul className="divide-y divide-gray-200">
              {recentEntries.map((entry) => (
                <li key={entry.id} className="py-3">
                  <div className="flex justify-between">
                    <div>
                      <p className="font-medium text-gray-900">{entry.entry}</p>
                      <p className="text-sm text-gray-500">
                        {new Date(entry.entryDate).toLocaleDateString()}
                      </p>
                    </div>
                    {entry.completed && (
                      <svg
                        className="h-5 w-5 text-green-500"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                      >
                        <path
                          fillRule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                          clipRule="evenodd"
                        />
                      </svg>
                    )}
                  </div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  )
}

export default Dashboard
