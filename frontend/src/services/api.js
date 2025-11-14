import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor to handle errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Unauthorized - clear token and redirect to login
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

// Authentication API
export const authAPI = {
  register: (data) => api.post('/api/auth/register', data),
  login: (data) => api.post('/api/auth/login', data),
}

// User API
export const userAPI = {
  getProfile: () => api.get('/api/users/profile'),
  updateProfile: (data) => api.put('/api/users/profile', data),
  changePassword: (data) => api.put('/api/users/password', data),
  getAllUsers: () => api.get('/api/users'),
  deleteUser: (id) => api.delete(`/api/users/${id}`),
}

// Habit API
export const habitAPI = {
  getAll: () => api.get('/api/habits'),
  getByJournal: (journalId) => api.get(`/api/habits/journal/${journalId}`),
  getById: (id) => api.get(`/api/habits/${id}`),
  create: (data) => api.post('/api/habits', data),
  update: (id, data) => api.put(`/api/habits/${id}`, data),
  delete: (id) => api.delete(`/api/habits/${id}`),
}

// Entry API
export const entryAPI = {
  getAll: () => api.get('/api/entries'),
  getByHabit: (habitId) => api.get(`/api/entries/habit/${habitId}`),
  getByJournal: (journalId) => api.get(`/api/entries/journal/${journalId}`),
  getByDateRange: (habitId, startDate, endDate) =>
    api.get(`/api/entries/habit/${habitId}/range`, { params: { startDate, endDate } }),
  create: (data) => api.post('/api/entries', data),
  update: (id, data) => api.put(`/api/entries/${id}`, data),
  delete: (id) => api.delete(`/api/entries/${id}`),
}

// Milestone API
export const milestoneAPI = {
  getAll: () => api.get('/api/milestones'),
  getByHabit: (habitId) => api.get(`/api/milestones/habit/${habitId}`),
  getByJournal: (journalId) => api.get(`/api/milestones/journal/${journalId}`),
  getById: (id) => api.get(`/api/milestones/${id}`),
  create: (data) => api.post('/api/milestones', data),
  update: (id, data) => api.put(`/api/milestones/${id}`, data),
  updateStatus: (id, status) => api.patch(`/api/milestones/${id}/status`, { status }),
  delete: (id) => api.delete(`/api/milestones/${id}`),
}

// Journal API
export const journalAPI = {
  getAll: () => api.get('/api/journals'),
  getById: (id) => api.get(`/api/journals/${id}`),
  getByUser: (userId) => api.get(`/api/journals/user/${userId}`),
  create: (data) => api.post('/api/journals', data),
  update: (id, data) => api.put(`/api/journals/${id}`, data),
  addHabit: (id, habitId) => api.post(`/api/journals/${id}/habits/${habitId}`),
  removeHabit: (id, habitId) => api.delete(`/api/journals/${id}/habits/${habitId}`),
  delete: (id) => api.delete(`/api/journals/${id}`),
}

export default api
