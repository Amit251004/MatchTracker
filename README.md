# Match Finder App

Android application built using Java.

Features:
- Navigation Drawer
- Retrofit API Integration
- RecyclerView
- SQLite Database
- Save/Unsave Venues
- Dark Mode Support

Tech Stack:
- Java
- Retrofit
- SQLite
- Material Design

Key Features
1.Navigation Drawer — with header (app name + user info), two main nav items, Settings/About
2.All Venues — fetches 30 venues from Foursquare API, shows name, address, category chip with emoji icon
3.Save/Unsave — star toggle saves venue ID + name to SQLite
4.Saved Venues — shows saved list from DB, star removes it, empty state when nothing saved
5.Star sync — All Venues reflects correct star state based on DB
6.Dark mode — adapts automatically to system setting
7.Responsive layout — text ellipsizes and wraps, no overlapping on any screen size

