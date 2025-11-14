import { useState, useEffect } from 'react'

const MilestoneModal = ({ milestone, habits, onClose, onSave }) => {
  const [formData, setFormData] = useState({
    habitId: '',
    name: '',
    description: '',
    goalUnits: '',
    dueDate: '',
  })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    if (milestone) {
      setFormData({
        habitId: milestone.habitId || '',
        name: milestone.name || '',
        description: milestone.description || '',
        goalUnits: milestone.goalUnits || '',
        dueDate: milestone.dueDate
          ? new Date(milestone.dueDate).toISOString().split('T')[0]
          : '',
      })
    } else if (habits.length > 0) {
      setFormData((prev) => ({ ...prev, habitId: habits[0].id }))
    }
  }, [milestone, habits])

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')

    if (!formData.habitId || !formData.name || !formData.description) {
      setError('Habit, name, and description are required')
      return
    }

    setLoading(true)

    try {
      await onSave({
        ...formData,
        goalUnits: formData.goalUnits ? parseInt(formData.goalUnits) : null,
        status: milestone?.status || 'NOT_STARTED',
      })
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save milestone')
      setLoading(false)
    }
  }

  return (
    <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg p-8 max-w-md w-full mx-4">
        <h2 className="text-2xl font-bold text-gray-900 mb-6">
          {milestone ? 'Edit Milestone' : 'Create Milestone'}
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
                placeholder="e.g., 30 Days Streak"
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
                placeholder="Describe your goal..."
              />
            </div>

            <div>
              <label htmlFor="goalUnits" className="block text-sm font-medium text-gray-700">
                Goal (number of completions)
              </label>
              <input
                type="number"
                id="goalUnits"
                name="goalUnits"
                min="1"
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.goalUnits}
                onChange={handleChange}
                placeholder="30"
              />
            </div>

            <div>
              <label htmlFor="dueDate" className="block text-sm font-medium text-gray-700">
                Due Date
              </label>
              <input
                type="date"
                id="dueDate"
                name="dueDate"
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                value={formData.dueDate}
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
              {loading ? 'Saving...' : milestone ? 'Update' : 'Create'}
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

export default MilestoneModal
