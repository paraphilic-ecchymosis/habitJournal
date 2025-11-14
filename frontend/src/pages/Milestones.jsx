import { useState, useEffect } from 'react'
import { milestoneAPI, habitAPI } from '../services/api'
import MilestoneModal from '../components/MilestoneModal'

const Milestones = () => {
  const [milestones, setMilestones] = useState([])
  const [habits, setHabits] = useState([])
  const [loading, setLoading] = useState(true)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [editingMilestone, setEditingMilestone] = useState(null)

  useEffect(() => {
    fetchData()
  }, [])

  const fetchData = async () => {
    try {
      const [milestonesRes, habitsRes] = await Promise.all([
        milestoneAPI.getAll(),
        habitAPI.getAll(),
      ])
      setMilestones(milestonesRes.data)
      setHabits(habitsRes.data)
    } catch (error) {
      console.error('Error fetching data:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleCreateMilestone = () => {
    setEditingMilestone(null)
    setIsModalOpen(true)
  }

  const handleEditMilestone = (milestone) => {
    setEditingMilestone(milestone)
    setIsModalOpen(true)
  }

  const handleDeleteMilestone = async (milestoneId) => {
    if (!window.confirm('Are you sure you want to delete this milestone?')) {
      return
    }

    try {
      await milestoneAPI.delete(milestoneId)
      setMilestones(milestones.filter((m) => m.id !== milestoneId))
    } catch (error) {
      console.error('Error deleting milestone:', error)
      alert('Failed to delete milestone')
    }
  }

  const handleSaveMilestone = async (milestoneData) => {
    try {
      if (editingMilestone) {
        const response = await milestoneAPI.update(editingMilestone.id, milestoneData)
        setMilestones(milestones.map((m) => (m.id === editingMilestone.id ? response.data : m)))
      } else {
        const response = await milestoneAPI.create(milestoneData)
        setMilestones([...milestones, response.data])
      }
      setIsModalOpen(false)
      setEditingMilestone(null)
    } catch (error) {
      console.error('Error saving milestone:', error)
      throw error
    }
  }

  const handleStatusChange = async (milestoneId, newStatus) => {
    try {
      const response = await milestoneAPI.updateStatus(milestoneId, newStatus)
      setMilestones(milestones.map((m) => (m.id === milestoneId ? response.data : m)))
    } catch (error) {
      console.error('Error updating status:', error)
      alert('Failed to update milestone status')
    }
  }

  const getHabitName = (habitId) => {
    const habit = habits.find((h) => h.id === habitId)
    return habit ? habit.name : 'Unknown Habit'
  }

  const getProgressPercentage = (milestone) => {
    if (!milestone.goalUnits || milestone.goalUnits === 0) return 0
    const completed = milestone.completedUnits || 0
    return Math.min((completed / milestone.goalUnits) * 100, 100)
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'NOT_STARTED':
        return 'bg-gray-100 text-gray-800'
      case 'IN_PROGRESS':
        return 'bg-blue-100 text-blue-800'
      case 'COMPLETED':
        return 'bg-green-100 text-green-800'
      case 'FAILED':
        return 'bg-red-100 text-red-800'
      case 'CANCELLED':
        return 'bg-yellow-100 text-yellow-800'
      default:
        return 'bg-gray-100 text-gray-800'
    }
  }

  const formatStatus = (status) => {
    return status.replace('_', ' ').toLowerCase().replace(/\b\w/g, (l) => l.toUpperCase())
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
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Milestones</h1>
        <button
          onClick={handleCreateMilestone}
          className="bg-primary-600 hover:bg-primary-700 text-white px-4 py-2 rounded-md text-sm font-medium"
        >
          Create Milestone
        </button>
      </div>

      {milestones.length === 0 ? (
        <div className="text-center py-12">
          <svg
            className="mx-auto h-12 w-12 text-gray-400"
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
          <h3 className="mt-2 text-sm font-medium text-gray-900">No milestones</h3>
          <p className="mt-1 text-sm text-gray-500">Get started by creating a new milestone.</p>
        </div>
      ) : (
        <div className="space-y-6">
          {milestones.map((milestone) => {
            const progress = getProgressPercentage(milestone)
            return (
              <div key={milestone.id} className="bg-white shadow rounded-lg p-6">
                <div className="flex justify-between items-start mb-4">
                  <div className="flex-1">
                    <div className="flex items-center justify-between mb-2">
                      <h3 className="text-lg font-semibold text-gray-900">{milestone.name}</h3>
                      <span
                        className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(
                          milestone.status
                        )}`}
                      >
                        {formatStatus(milestone.status)}
                      </span>
                    </div>
                    <p className="text-gray-600 text-sm mb-3">{milestone.description}</p>
                    <div className="flex items-center space-x-4 text-sm text-gray-500">
                      <span className="font-medium text-primary-600">
                        {getHabitName(milestone.habitId)}
                      </span>
                      {milestone.dueDate && (
                        <span>Due: {new Date(milestone.dueDate).toLocaleDateString()}</span>
                      )}
                    </div>
                  </div>
                  <div className="flex space-x-2 ml-4">
                    <button
                      onClick={() => handleEditMilestone(milestone)}
                      className="text-primary-600 hover:text-primary-700"
                    >
                      <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
                        />
                      </svg>
                    </button>
                    <button
                      onClick={() => handleDeleteMilestone(milestone.id)}
                      className="text-red-600 hover:text-red-700"
                    >
                      <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                        />
                      </svg>
                    </button>
                  </div>
                </div>

                {/* Progress Bar */}
                {milestone.goalUnits && (
                  <div className="mb-4">
                    <div className="flex justify-between text-sm mb-1">
                      <span className="text-gray-600">
                        Progress: {milestone.completedUnits || 0} / {milestone.goalUnits}
                      </span>
                      <span className="text-gray-600">{Math.round(progress)}%</span>
                    </div>
                    <div className="w-full bg-gray-200 rounded-full h-2">
                      <div
                        className="bg-primary-600 h-2 rounded-full transition-all duration-300"
                        style={{ width: `${progress}%` }}
                      ></div>
                    </div>
                  </div>
                )}

                {/* Status Actions */}
                {milestone.status === 'NOT_STARTED' && (
                  <button
                    onClick={() => handleStatusChange(milestone.id, 'IN_PROGRESS')}
                    className="mt-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md text-sm"
                  >
                    Start Milestone
                  </button>
                )}
                {milestone.status === 'IN_PROGRESS' && (
                  <div className="mt-2 flex space-x-2">
                    <button
                      onClick={() => handleStatusChange(milestone.id, 'COMPLETED')}
                      className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-md text-sm"
                    >
                      Mark Complete
                    </button>
                    <button
                      onClick={() => handleStatusChange(milestone.id, 'FAILED')}
                      className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md text-sm"
                    >
                      Mark Failed
                    </button>
                  </div>
                )}
              </div>
            )
          })}
        </div>
      )}

      {isModalOpen && (
        <MilestoneModal
          milestone={editingMilestone}
          habits={habits}
          onClose={() => {
            setIsModalOpen(false)
            setEditingMilestone(null)
          }}
          onSave={handleSaveMilestone}
        />
      )}
    </div>
  )
}

export default Milestones
