import { useState, useEffect } from 'react'
import { analyticsAPI, aiAPI, exportAPI } from '../services/api'
import { LineChart, Line, BarChart, Bar, PieChart, Pie, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'
import { saveAs } from 'file-saver'

const Analytics = () => {
  const [analytics, setAnalytics] = useState(null)
  const [aiInsights, setAiInsights] = useState(null)
  const [loading, setLoading] = useState(true)
  const [exporting, setExporting] = useState(false)

  useEffect(() => {
    fetchAnalytics()
  }, [])

  const fetchAnalytics = async () => {
    try {
      const [analyticsRes, insightsRes] = await Promise.all([
        analyticsAPI.getAnalytics(),
        aiAPI.getOverallInsights().catch(() => null), // AI might not be configured
      ])
      setAnalytics(analyticsRes.data)
      if (insightsRes) {
        setAiInsights(insightsRes.data)
      }
    } catch (error) {
      console.error('Error fetching analytics:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleExportPDF = async () => {
    setExporting(true)
    try {
      const response = await exportAPI.exportReportPDF()
      const blob = new Blob([response.data], { type: 'application/pdf' })
      saveAs(blob, `habit-report-${new Date().toISOString().split('T')[0]}.pdf`)
    } catch (error) {
      console.error('Error exporting PDF:', error)
      alert('Failed to export PDF')
    } finally {
      setExporting(false)
    }
  }

  const handleExportCSV = async (type) => {
    setExporting(true)
    try {
      let response
      let filename
      switch (type) {
        case 'habits':
          response = await exportAPI.exportHabitsCSV()
          filename = 'habits'
          break
        case 'entries':
          response = await exportAPI.exportEntriesCSV()
          filename = 'entries'
          break
        case 'milestones':
          response = await exportAPI.exportMilestonesCSV()
          filename = 'milestones'
          break
        default:
          return
      }
      const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
      saveAs(blob, `${filename}-${new Date().toISOString().split('T')[0]}.xlsx`)
    } catch (error) {
      console.error('Error exporting CSV:', error)
      alert('Failed to export data')
    } finally {
      setExporting(false)
    }
  }

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    )
  }

  if (!analytics) {
    return <div className="text-center py-12">No analytics data available</div>
  }

  const COLORS = ['#0ea5e9', '#10b981', '#f59e0b', '#ef4444', '#8b5cf6']

  // Prepare data for charts
  const streakData = analytics.streaks.map(streak => ({
    name: streak.habitName.substring(0, 15),
    current: streak.currentStreak,
    longest: streak.longestStreak,
  }))

  const completionData = analytics.completionRates.map(rate => ({
    name: rate.habitName.substring(0, 15),
    rate: parseFloat(rate.rate.toFixed(2)),
  }))

  const statsData = [
    { name: 'Active Habits', value: analytics.habitStats.activeHabits },
    { name: 'Inactive Habits', value: analytics.habitStats.totalHabits - analytics.habitStats.activeHabits },
  ]

  return (
    <div className="px-4 sm:px-0">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-gray-900 dark:text-white">Analytics Dashboard</h1>
        <div className="flex space-x-2">
          <button
            onClick={() => handleExportCSV('habits')}
            disabled={exporting}
            className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-md text-sm font-medium disabled:opacity-50"
          >
            Export Habits
          </button>
          <button
            onClick={() => handleExportCSV('entries')}
            disabled={exporting}
            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md text-sm font-medium disabled:opacity-50"
          >
            Export Entries
          </button>
          <button
            onClick={handleExportPDF}
            disabled={exporting}
            className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md text-sm font-medium disabled:opacity-50"
          >
            Export PDF Report
          </button>
        </div>
      </div>

      {/* AI Insights */}
      {aiInsights && (
        <div className="bg-gradient-to-r from-blue-500 to-purple-600 rounded-lg p-6 mb-8 text-white shadow-lg">
          <h2 className="text-xl font-bold mb-3 flex items-center">
            <svg className="w-6 h-6 mr-2" fill="currentColor" viewBox="0 0 20 20">
              <path d="M9 2a1 1 0 000 2h2a1 1 0 100-2H9z" />
              <path fillRule="evenodd" d="M4 5a2 2 0 012-2 3 3 0 003 3h2a3 3 0 003-3 2 2 0 012 2v11a2 2 0 01-2 2H6a2 2 0 01-2-2V5zm9.707 5.707a1 1 0 00-1.414-1.414L9 12.586l-1.293-1.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
            </svg>
            AI-Powered Insights
          </h2>
          <p className="text-lg">{aiInsights.insight}</p>
        </div>
      )}

      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div className="bg-white dark:bg-gray-800 overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-blue-500 rounded-md p-3">
                <svg className="h-6 w-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                </svg>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 dark:text-gray-400 truncate">Completion Rate</dt>
                  <dd className="text-lg font-semibold text-gray-900 dark:text-white">{analytics.progressSummary.overallCompletionRate.toFixed(1)}%</dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white dark:bg-gray-800 overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-green-500 rounded-md p-3">
                <svg className="h-6 w-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                </svg>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 dark:text-gray-400 truncate">Days Tracked</dt>
                  <dd className="text-lg font-semibold text-gray-900 dark:text-white">{analytics.progressSummary.totalDaysTracked}</dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white dark:bg-gray-800 overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-purple-500 rounded-md p-3">
                <svg className="h-6 w-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                </svg>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 dark:text-gray-400 truncate">Top Performer</dt>
                  <dd className="text-lg font-semibold text-gray-900 dark:text-white truncate">{analytics.progressSummary.mostConsistentHabit}</dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white dark:bg-gray-800 overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-yellow-500 rounded-md p-3">
                <svg className="h-6 w-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                </svg>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 dark:text-gray-400 truncate">Needs Attention</dt>
                  <dd className="text-lg font-semibold text-gray-900 dark:text-white truncate">{analytics.progressSummary.needsAttention}</dd>
                </dl>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
        {/* Streak Chart */}
        <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-4">Habit Streaks</h3>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={streakData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="name" angle={-45} textAnchor="end" height={80} />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="current" fill="#0ea5e9" name="Current Streak" />
              <Bar dataKey="longest" fill="#10b981" name="Longest Streak" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        {/* Completion Rate Chart */}
        <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-4">Completion Rates</h3>
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={completionData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="name" angle={-45} textAnchor="end" height={80} />
              <YAxis domain={[0, 100]} />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="rate" stroke="#8b5cf6" strokeWidth={2} name="Completion Rate %" />
            </LineChart>
          </ResponsiveContainer>
        </div>

        {/* Active vs Inactive Pie Chart */}
        <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-4">Active Habits</h3>
          <ResponsiveContainer width="100%" height={300}>
            <PieChart>
              <Pie
                data={statsData}
                cx="50%"
                cy="50%"
                labelLine={false}
                label={({ name, value }) => `${name}: ${value}`}
                outerRadius={80}
                fill="#8884d8"
                dataKey="value"
              >
                {statsData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </ResponsiveContainer>
        </div>

        {/* Summary Table */}
        <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-4">Quick Stats</h3>
          <div className="space-y-3">
            <div className="flex justify-between py-2 border-b border-gray-200 dark:border-gray-700">
              <span className="text-gray-600 dark:text-gray-400">Total Habits:</span>
              <span className="font-semibold text-gray-900 dark:text-white">{analytics.habitStats.totalHabits}</span>
            </div>
            <div className="flex justify-between py-2 border-b border-gray-200 dark:border-gray-700">
              <span className="text-gray-600 dark:text-gray-400">Active Habits:</span>
              <span className="font-semibold text-gray-900 dark:text-white">{analytics.habitStats.activeHabits}</span>
            </div>
            <div className="flex justify-between py-2 border-b border-gray-200 dark:border-gray-700">
              <span className="text-gray-600 dark:text-gray-400">Total Entries:</span>
              <span className="font-semibold text-gray-900 dark:text-white">{analytics.habitStats.totalEntries}</span>
            </div>
            <div className="flex justify-between py-2">
              <span className="text-gray-600 dark:text-gray-400">Completed Milestones:</span>
              <span className="font-semibold text-gray-900 dark:text-white">{analytics.habitStats.completedMilestones}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Analytics
