import { useState, useEffect } from 'react'

const EntryModal = ({ entry, habits, onClose, onSave }) => {
  const [formData, setFormData] = useState({
    habitId: '',
    entry: '',
    entryDate: new Date().toISOString().split('T')[0],
    completed: false,
    notes: '',
  })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    if (entry) {
      setFormData({
        habitId: entry.habitId || '',
        entry: entry.entry || '',
        entryDate: entry.entryDate
          ? new Date(entry.entryDate).toISOString().split('T')[0]
          : new Date().toISOString().split('T')[0],
        completed: entry.completed || false,
        notes: entry.notes || '',
      })
    } else if (habits.length > 0) {
      setFormData((prev) => ({ ...prev, habitId: habits[0].id }))
    }
  }, [entry, habits])

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')

    if (!formData.habitId || !formData.entry) {
      setError('Habit and entry text are required')
      return
    }

    setLoading(true)

    try {
      await onSave(formData)
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save entry')
      setLoading(false)
    }
  }

  return (
    <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg p-8 max-w-md w-full mx-4">
        <h2 className="text-2xl font-bold text-gray-900 mb-6">
          {entry ? 'Edit Entry' : 'New Entry'}
        </h2>

        {error && (
          <div className="mb-4 rounded-md bg-red-50 p-4">
            <div className="text-sm text-red-800">{error}</div>
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="space-y-4">
            <div>
              <label htmlFor="habitId" className="block text-sm font-medium text-gray-700">
                Habit *
              </label>
              <select
                id="habitId"
                name="habitId"
                required
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.habitId}
                onChange={handleChange}
              >
                {habits.map((habit) => (
                  <option key={habit.id} value={habit.id}>
                    {habit.name}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label htmlFor="entry" className="block text-sm font-medium text-gray-700">
                Entry *
              </label>
              <input
                type="text"
                id="entry"
                name="entry"
                required
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.entry}
                onChange={handleChange}
                placeholder="What did you accomplish?"
              />
            </div>

            <div>
              <label htmlFor="entryDate" className="block text-sm font-medium text-gray-700">
                Date
              </label>
              <input
                type="date"
                id="entryDate"
                name="entryDate"
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.entryDate}
                onChange={handleChange}
              />
            </div>

            <div>
              <label htmlFor="notes" className="block text-sm font-medium text-gray-700">
                Notes
              </label>
              <textarea
                id="notes"
                name="notes"
                rows={3}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.notes}
                onChange={handleChange}
                placeholder="Additional details..."
              />
            </div>

            <div className="flex items-center">
              <input
                type="checkbox"
                id="completed"
                name="completed"
                className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                checked={formData.completed}
                onChange={handleChange}
              />
              <label htmlFor="completed" className="ml-2 block text-sm text-gray-700">
                Mark as completed
              </label>
            </div>
          </div>

          <div className="mt-6 flex space-x-3">
            <button
              type="submit"
              disabled={loading}
              className="flex-1 bg-primary-600 hover:bg-primary-700 text-white px-4 py-2 rounded-md text-sm font-medium disabled:opacity-50"
            >
              {loading ? 'Saving...' : entry ? 'Update' : 'Create'}
            </button>
            <button
              type="button"
              onClick={onClose}
              className="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-800 px-4 py-2 rounded-md text-sm font-medium"
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default EntryModal
