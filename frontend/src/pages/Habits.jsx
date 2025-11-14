import { useState, useEffect } from 'react'
import { habitAPI, journalAPI } from '../services/api'
import HabitModal from '../components/HabitModal'

const Habits = () => {
  const [habits, setHabits] = useState([])
  const [loading, setLoading] = useState(true)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [editingHabit, setEditingHabit] = useState(null)
  const [journalId, setJournalId] = useState(null)

  useEffect(() => {
    fetchJournalAndHabits()
  }, [])

  const fetchJournalAndHabits = async () => {
    try {
      // Get user's journal (assuming one journal per user for now)
      const journalsRes = await journalAPI.getAll()
      const journals = journalsRes.data

      if (journals.length > 0) {
        setJournalId(journals[0].id)
        const habitsRes = await habitAPI.getAll()
        setHabits(habitsRes.data)
      }
    } catch (error) {
      console.error('Error fetching habits:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleCreateHabit = () => {
    setEditingHabit(null)
    setIsModalOpen(true)
  }

  const handleEditHabit = (habit) => {
    setEditingHabit(habit)
    setIsModalOpen(true)
  }

  const handleDeleteHabit = async (habitId) => {
    if (!window.confirm('Are you sure you want to delete this habit?')) {
      return
    }

    try {
      await habitAPI.delete(habitId)
      setHabits(habits.filter((h) => h.id !== habitId))
    } catch (error) {
      console.error('Error deleting habit:', error)
      alert('Failed to delete habit')
    }
  }

  const handleSaveHabit = async (habitData) => {
    try {
      if (editingHabit) {
        // Update existing habit
        const response = await habitAPI.update(editingHabit.id, habitData)
        setHabits(habits.map((h) => (h.id === editingHabit.id ? response.data : h)))
      } else {
        // Create new habit
        const response = await habitAPI.create({ ...habitData, journalId })
        setHabits([...habits, response.data])
      }
      setIsModalOpen(false)
      setEditingHabit(null)
    } catch (error) {
      console.error('Error saving habit:', error)
      throw error
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
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Habits</h1>
        <button
          onClick={handleCreateHabit}
          className="bg-primary-600 hover:bg-primary-700 text-white px-4 py-2 rounded-md text-sm font-medium"
        >
          Create Habit
        </button>
      </div>

      {habits.length === 0 ? (
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
              d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"
            />
          </svg>
          <h3 className="mt-2 text-sm font-medium text-gray-900">No habits</h3>
          <p className="mt-1 text-sm text-gray-500">Get started by creating a new habit.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {habits.map((habit) => (
            <div key={habit.id} className="bg-white shadow rounded-lg p-6">
              <div className="flex justify-between items-start mb-4">
                <h3 className="text-lg font-semibold text-gray-900">{habit.name}</h3>
                <div className="flex space-x-2">
                  <button
                    onClick={() => handleEditHabit(habit)}
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
                    onClick={() => handleDeleteHabit(habit.id)}
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
              <p className="text-gray-600 text-sm mb-4">{habit.description}</p>
              <div className="space-y-2 text-sm">
                <div className="flex justify-between">
                  <span className="text-gray-500">Frequency:</span>
                  <span className="font-medium text-gray-900">{habit.frequency}</span>
                </div>
                {habit.startDate && (
                  <div className="flex justify-between">
                    <span className="text-gray-500">Started:</span>
                    <span className="font-medium text-gray-900">
                      {new Date(habit.startDate).toLocaleDateString()}
                    </span>
                  </div>
                )}
                {habit.goalUnits && (
                  <div className="flex justify-between">
                    <span className="text-gray-500">Goal:</span>
                    <span className="font-medium text-gray-900">{habit.goalUnits} times</span>
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
      )}

      {isModalOpen && (
        <HabitModal
          habit={editingHabit}
          onClose={() => {
            setIsModalOpen(false)
            setEditingHabit(null)
          }}
          onSave={handleSaveHabit}
        />
      )}
    </div>
  )
}

export default Habits
