# Habit Journal - React Frontend

A modern, responsive React frontend for the Habit Journal application built with Vite, React Router, and Tailwind CSS.

## Features

- **Authentication**
  - User registration with validation
  - Login with JWT token management
  - Protected routes for authenticated users
  - Automatic token refresh and logout on expiration

- **Dashboard**
  - Overview of habits, entries, and milestones
  - Statistics cards showing total counts
  - Recent activity display
  - Quick navigation to all sections

- **Habit Management**
  - Create, read, update, and delete habits
  - Track habit frequency (Daily, Weekly, Monthly, Yearly)
  - Set goals and start dates
  - Responsive card-based layout

- **Journal Entries**
  - Log habit completions with dates
  - Add notes and details to entries
  - Filter entries by habit and date range
  - Mark entries as completed

- **Milestone Tracking**
  - Create milestones linked to habits
  - Track progress with visual progress bars
  - Update milestone status (Not Started, In Progress, Completed, Failed, Cancelled)
  - Set goals and due dates

## Tech Stack

- **React 18** - Modern React with hooks
- **Vite** - Fast build tool and dev server
- **React Router 6** - Client-side routing
- **Axios** - HTTP client for API communication
- **Tailwind CSS** - Utility-first CSS framework
- **Context API** - State management for authentication

## Project Structure

```
frontend/
├── public/              # Static assets
├── src/
│   ├── components/      # Reusable components
│   │   ├── Layout.jsx           # Main layout with navigation
│   │   ├── ProtectedRoute.jsx   # Route guard for authentication
│   │   ├── HabitModal.jsx       # Modal for habit CRUD
│   │   ├── EntryModal.jsx       # Modal for entry CRUD
│   │   └── MilestoneModal.jsx   # Modal for milestone CRUD
│   ├── context/         # React context providers
│   │   └── AuthContext.jsx      # Authentication state management
│   ├── pages/           # Page components
│   │   ├── Login.jsx            # Login page
│   │   ├── Register.jsx         # Registration page
│   │   ├── Dashboard.jsx        # Dashboard overview
│   │   ├── Habits.jsx           # Habits management
│   │   ├── Entries.jsx          # Journal entries
│   │   └── Milestones.jsx       # Milestone tracking
│   ├── services/        # API services
│   │   └── api.js               # Axios configuration and API endpoints
│   ├── App.jsx          # Main app component with routing
│   ├── main.jsx         # Application entry point
│   └── index.css        # Global styles and Tailwind imports
├── .env.example         # Environment variables template
├── index.html           # HTML template
├── package.json         # Dependencies and scripts
├── vite.config.js       # Vite configuration
├── tailwind.config.js   # Tailwind CSS configuration
└── postcss.config.js    # PostCSS configuration
```

## Getting Started

### Prerequisites

- Node.js 18+ and npm
- Backend API running on `http://localhost:8080` (see main project README)

### Installation

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Create environment file:
   ```bash
   cp .env.example .env
   ```

4. Configure environment variables in `.env`:
   ```
   VITE_API_BASE_URL=http://localhost:8080
   ```

### Development

Run the development server:
```bash
npm run dev
```

The application will be available at `http://localhost:3000`

### Build

Create a production build:
```bash
npm run build
```

The build output will be in the `dist/` directory.

### Preview

Preview the production build:
```bash
npm run preview
```

### Linting

Run ESLint:
```bash
npm run lint
```

## API Integration

The frontend communicates with the backend REST API through Axios. All API endpoints are defined in `src/services/api.js`.

### API Services

- **authAPI** - Authentication endpoints (login, register)
- **userAPI** - User management endpoints
- **habitAPI** - Habit CRUD operations
- **entryAPI** - Journal entry operations with filtering
- **milestoneAPI** - Milestone tracking and status updates
- **journalAPI** - Journal management

### Authentication Flow

1. User logs in through `/login` page
2. Backend returns JWT token
3. Token stored in localStorage
4. Axios interceptor adds token to all requests
5. Protected routes check for token
6. Automatic logout on 401 responses

## Features in Detail

### Dashboard
- Real-time statistics
- Quick overview of all activities
- Recent habits and entries display
- Links to detailed pages

### Habits Page
- Grid layout of all habits
- Create/Edit modal with validation
- Frequency selection (Daily, Weekly, Monthly, Yearly)
- Goal tracking
- Delete with confirmation

### Entries Page
- Chronological list of entries
- Filter by habit and date range
- Add notes and completion status
- Link to associated habit

### Milestones Page
- Visual progress tracking
- Status management workflow
- Progress bars showing completion percentage
- Due date tracking
- Status transitions (Start → Complete/Fail)

## Styling

The application uses Tailwind CSS for styling with a custom color palette:

- **Primary Color**: Blue (customizable in `tailwind.config.js`)
- **Responsive Design**: Mobile-first approach
- **Components**: Cards, modals, forms with consistent styling
- **Icons**: SVG icons from Heroicons

## Customization

### Changing Colors

Edit `tailwind.config.js` to customize the primary color palette:

```javascript
theme: {
  extend: {
    colors: {
      primary: {
        // Your custom color scale
      }
    }
  }
}
```

### Adding New Pages

1. Create component in `src/pages/`
2. Add route in `src/App.jsx`
3. Add navigation link in `src/components/Layout.jsx`

### API Configuration

Modify `src/services/api.js` to:
- Change base URL
- Add new endpoints
- Customize request/response interceptors

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Troubleshooting

### CORS Issues
Ensure backend CORS configuration allows `http://localhost:3000`

### API Connection Failed
- Check backend is running on port 8080
- Verify `VITE_API_BASE_URL` in `.env`
- Check network tab in browser DevTools

### Build Errors
- Clear node_modules and reinstall: `rm -rf node_modules && npm install`
- Clear Vite cache: `rm -rf node_modules/.vite`

## Future Enhancements

- [ ] AI-powered insights using Spring AI integration
- [ ] Charts and analytics visualizations
- [ ] Export data functionality
- [ ] Dark mode support
- [ ] Offline support with service workers
- [ ] Push notifications for habit reminders
- [ ] Social features (share progress)

## Contributing

1. Create a feature branch
2. Make your changes
3. Test thoroughly
4. Submit a pull request

## License

This project is part of the Habit Journal application.

---

**Built with** React, Vite, Tailwind CSS, and modern web technologies.
