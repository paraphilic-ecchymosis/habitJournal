import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const API_BASE_URL = 'http://localhost:8080'; // Change to your API URL

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add auth token
api.interceptors.request.use(
  async (config) => {
    const token = await AsyncStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle errors
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      // Unauthorized - clear token
      await AsyncStorage.removeItem('token');
      await AsyncStorage.removeItem('user');
    }
    return Promise.reject(error);
  }
);

// Authentication API
export const authAPI = {
  register: (data) => api.post('/api/auth/register', data),
  login: (data) => api.post('/api/auth/login', data),
};

// Habit API
export const habitAPI = {
  getAll: () => api.get('/api/habits'),
  getById: (id) => api.get(`/api/habits/${id}`),
  create: (data) => api.post('/api/habits', data),
  update: (id, data) => api.put(`/api/habits/${id}`, data),
  delete: (id) => api.delete(`/api/habits/${id}`),
};

// Entry API
export const entryAPI = {
  getAll: () => api.get('/api/entries'),
  getByHabit: (habitId) => api.get(`/api/entries/habit/${habitId}`),
  create: (data) => api.post('/api/entries', data),
  update: (id, data) => api.put(`/api/entries/${id}`, data),
  delete: (id) => api.delete(`/api/entries/${id}`),
};

// Milestone API
export const milestoneAPI = {
  getAll: () => api.get('/api/milestones'),
  getByHabit: (habitId) => api.get(`/api/milestones/habit/${habitId}`),
  create: (data) => api.post('/api/milestones', data),
  update: (id, data) => api.put(`/api/milestones/${id}`, data),
  updateStatus: (id, status) => api.patch(`/api/milestones/${id}/status`, { status }),
  delete: (id) => api.delete(`/api/milestones/${id}`),
};

// Analytics API
export const analyticsAPI = {
  getAnalytics: () => api.get('/api/analytics'),
  getHabitStreak: (habitId) => api.get(`/api/analytics/streak/${habitId}`),
};

export default api;
