import { useState, useEffect } from 'react'
import { entryAPI, habitAPI } from '../services/api'
import EntryModal from '../components/EntryModal'

const Entries = () => {
  const [entries, setEntries] = useState([])
  const [habits, setHabits] = useState([])
  const [loading, setLoading] = useState(true)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [editingEntry, setEditingEntry] = useState(null)
  const [selectedHabit, setSelectedHabit] = useState('all')
  const [startDate, setStartDate] = useState('')
  const [endDate, setEndDate] = useState('')

  useEffect(() => {
    fetchData()
  }, [])

  useEffect(() => {
    filterEntries()
  }, [selectedHabit, startDate, endDate])

  const fetchData = async () => {
    try {
      const [entriesRes, habitsRes] = await Promise.all([
        entryAPI.getAll(),
        habitAPI.getAll(),
      ])
      setEntries(entriesRes.data)
      setHabits(habitsRes.data)
    } catch (error) {
      console.error('Error fetching data:', error)
    } finally {
      setLoading(false)
    }
  }

  const filterEntries = async () => {
    try {
      if (selectedHabit !== 'all' && startDate && endDate) {
        const response = await entryAPI.getByDateRange(selectedHabit, startDate, endDate)
        setEntries(response.data)
      } else if (selectedHabit !== 'all') {
        const response = await entryAPI.getByHabit(selectedHabit)
        setEntries(response.data)
      } else {
        const response = await entryAPI.getAll()
        setEntries(response.data)
      }
    } catch (error) {
      console.error('Error filtering entries:', error)
    }
  }

  const handleCreateEntry = () => {
    setEditingEntry(null)
    setIsModalOpen(true)
  }

  const handleEditEntry = (entry) => {
    setEditingEntry(entry)
    setIsModalOpen(true)
  }

  const handleDeleteEntry = async (entryId) => {
    if (!window.confirm('Are you sure you want to delete this entry?')) {
      return
    }

    try {
      await entryAPI.delete(entryId)
      setEntries(entries.filter((e) => e.id !== entryId))
    } catch (error) {
      console.error('Error deleting entry:', error)
      alert('Failed to delete entry')
    }
  }

  const handleSaveEntry = async (entryData) => {
    try {
      if (editingEntry) {
        const response = await entryAPI.update(editingEntry.id, entryData)
        setEntries(entries.map((e) => (e.id === editingEntry.id ? response.data : e)))
      } else {
        const response = await entryAPI.create(entryData)
        setEntries([response.data, ...entries])
      }
      setIsModalOpen(false)
      setEditingEntry(null)
    } catch (error) {
      console.error('Error saving entry:', error)
      throw error
    }
  }

  const getHabitName = (habitId) => {
    const habit = habits.find((h) => h.id === habitId)
    return habit ? habit.name : 'Unknown Habit'
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
        <h1 className="text-3xl font-bold text-gray-900">Journal Entries</h1>
        <button
          onClick={handleCreateEntry}
          className="bg-primary-600 hover:bg-primary-700 text-white px-4 py-2 rounded-md text-sm font-medium"
        >
          New Entry
        </button>
      </div>

      {/* Filters */}
      <div className="bg-white shadow rounded-lg p-4 mb-6">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label htmlFor="habit-filter" className="block text-sm font-medium text-gray-700 mb-1">
              Filter by Habit
            </label>
            <select
              id="habit-filter"
              value={selectedHabit}
              onChange={(e) => setSelectedHabit(e.target.value)}
              className="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
            >
              <option value="all">All Habits</option>
              {habits.map((habit) => (
                <option key={habit.id} value={habit.id}>
                  {habit.name}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="start-date" className="block text-sm font-medium text-gray-700 mb-1">
              Start Date
            </label>
            <input
              type="date"
              id="start-date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
              className="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
            />
          </div>
          <div>
            <label htmlFor="end-date" className="block text-sm font-medium text-gray-700 mb-1">
              End Date
            </label>
            <input
              type="date"
              id="end-date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
              className="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
            />
          </div>
        </div>
      </div>

      {/* Entries List */}
      {entries.length === 0 ? (
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
              d="M12 6v6m0 0v6m0-6h6m-6 0H6"
            />
          </svg>
          <h3 className="mt-2 text-sm font-medium text-gray-900">No entries</h3>
          <p className="mt-1 text-sm text-gray-500">Get started by creating a new entry.</p>
        </div>
      ) : (
        <div className="space-y-4">
          {entries.map((entry) => (
            <div key={entry.id} className="bg-white shadow rounded-lg p-6">
              <div className="flex justify-between items-start">
                <div className="flex-1">
                  <div className="flex items-center justify-between mb-2">
                    <h3 className="text-lg font-semibold text-gray-900">{entry.entry}</h3>
                    {entry.completed && (
                      <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                        Completed
                      </span>
                    )}
                  </div>
                  <div className="flex items-center space-x-4 text-sm text-gray-500 mb-2">
                    <span className="font-medium text-primary-600">
                      {getHabitName(entry.habitId)}
                    </span>
                    <span>{new Date(entry.entryDate).toLocaleDateString()}</span>
                  </div>
                  {entry.notes && <p className="text-gray-600 text-sm">{entry.notes}</p>}
                </div>
                <div className="flex space-x-2 ml-4">
                  <button
                    onClick={() => handleEditEntry(entry)}
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
                    onClick={() => handleDeleteEntry(entry.id)}
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
            </div>
          ))}
        </div>
      )}

      {isModalOpen && (
        <EntryModal
          entry={editingEntry}
          habits={habits}
          onClose={() => {
            setIsModalOpen(false)
            setEditingEntry(null)
          }}
          onSave={handleSaveEntry}
        />
      )}
    </div>
  )
}

export default Entries
