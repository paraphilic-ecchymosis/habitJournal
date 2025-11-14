import { useState, useEffect } from 'react'

const HabitModal = ({ habit, onClose, onSave }) => {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    frequency: 'DAILY',
    startDate: new Date().toISOString().split('T')[0],
    goalUnits: '',
  })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    if (habit) {
      setFormData({
        name: habit.name || '',
        description: habit.description || '',
        frequency: habit.frequency || 'DAILY',
        startDate: habit.startDate
          ? new Date(habit.startDate).toISOString().split('T')[0]
          : new Date().toISOString().split('T')[0],
        goalUnits: habit.goalUnits || '',
      })
    }
  }, [habit])

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')

    if (!formData.name || !formData.description) {
      setError('Name and description are required')
      return
    }

    setLoading(true)

    try {
      await onSave({
        ...formData,
        goalUnits: formData.goalUnits ? parseInt(formData.goalUnits) : null,
      })
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save habit')
      setLoading(false)
    }
  }

  return (
    <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg p-8 max-w-md w-full mx-4">
        <h2 className="text-2xl font-bold text-gray-900 mb-6">
          {habit ? 'Edit Habit' : 'Create Habit'}
        </h2>

        {error && (
          <div className="mb-4 rounded-md bg-red-50 p-4">
            <div className="text-sm text-red-800">{error}</div>
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="space-y-4">
            <div>
              <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                Name *
              </label>
              <input
                type="text"
                id="name"
                name="name"
                required
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.name}
                onChange={handleChange}
              />
            </div>

            <div>
              <label htmlFor="description" className="block text-sm font-medium text-gray-700">
                Description *
              </label>
              <textarea
                id="description"
                name="description"
                required
                rows={3}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.description}
                onChange={handleChange}
              />
            </div>

            <div>
              <label htmlFor="frequency" className="block text-sm font-medium text-gray-700">
                Frequency
              </label>
              <select
                id="frequency"
                name="frequency"
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.frequency}
                onChange={handleChange}
              >
                <option value="DAILY">Daily</option>
                <option value="WEEKLY">Weekly</option>
                <option value="MONTHLY">Monthly</option>
                <option value="YEARLY">Yearly</option>
              </select>
            </div>

            <div>
              <label htmlFor="startDate" className="block text-sm font-medium text-gray-700">
                Start Date
              </label>
              <input
                type="date"
                id="startDate"
                name="startDate"
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.startDate}
                onChange={handleChange}
              />
            </div>

            <div>
              <label htmlFor="goalUnits" className="block text-sm font-medium text-gray-700">
                Goal (number of times)
              </label>
              <input
                type="number"
                id="goalUnits"
                name="goalUnits"
                min="1"
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.goalUnits}
                onChange={handleChange}
              />
            </div>
          </div>

          <div className="mt-6 flex space-x-3">
            <button
              type="submit"
              disabled={loading}
              className="flex-1 bg-primary-600 hover:bg-primary-700 text-white px-4 py-2 rounded-md text-sm font-medium disabled:opacity-50"
            >
              {loading ? 'Saving...' : habit ? 'Update' : 'Create'}
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

export default HabitModal
