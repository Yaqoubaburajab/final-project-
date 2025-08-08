import React, { useState, useEffect } from 'react';
import axios from 'axios';

function CoursesPage() {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/courses' );
                setCourses(response.data);
            } catch (err) {
                setError('Failed to load courses.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };
        fetchCourses();
    }, []);

    if (loading) return <p>Loading courses...</p>;
    if (error) return <p className="error">{error}</p>;

    return (
        <div className="page-content">
            <h2>Available Courses</h2>
            <div className="course-list">
                {courses.length > 0 ? (
                    courses.map(course => (
                        <div key={course.id} className="course-card">
                            <h3>{course.title}</h3>
                            <p>{course.description}</p>
                        </div>
                    ))
                ) : (
                    <p>No courses available at the moment.</p>
                )}
            </div>
        </div>
    );
}

export default CoursesPage;
