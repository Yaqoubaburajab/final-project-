import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

function Navbar() {
    return (
        <nav className="navbar">
            <Link to="/" className="navbar-brand">LMS</Link>
            <div className="navbar-links">
                <Link to="/">Home</Link>
                <Link to="/courses">Courses</Link>
                <Link to="/login">Login</Link>
                <Link to="/register">Register</Link>
            </div>
        </nav>
    );
}

export default Navbar;
