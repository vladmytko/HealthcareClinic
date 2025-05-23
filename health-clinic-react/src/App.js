// scr/App.js
import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import LoginPage from './component/auth/LoginPage';
import RegisterPage from "./component/auth/RegisterPage";
import Navbar from './component/common/NavBar'
import PatientProfilePage from "./component/profile/PatientProfilePage";
import PatientEditProfile from "./component/profile/PatientEditProfile";

import {ProtectedRoute, AdminRoute } from './service/guard';

function App() {
  return (
    <BrowserRouter>
     <div className="App">
      <Navbar />
        <div className="content">
          <Routes>
            {/* Public Routes */}
            <Route exact path="/login" element={<LoginPage/>} />
            <Route exact path="/register" element={<RegisterPage/>}/>

            {/* Protected Routes */}
            <Route path="/patient-profile"
              element={<ProtectedRoute element={<PatientProfilePage/>}/>}
            />

            <Route path="/edit-patient-profile"
              element={<ProtectedRoute element={<PatientEditProfile/>}/>}
            />
            {/* Fallback Route */}
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </div>
     </div>

    </BrowserRouter>
  );
}

export default App;
