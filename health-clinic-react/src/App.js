// scr/App.js
import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from './component/auth/LoginPage';
import RegisterPage from "./component/auth/RegisterPage";
import Navbar from './component/common/NavBar'

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


            {/* Fallback Route */}
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </div>
     </div>

    </BrowserRouter>
  );
}

export default App;
