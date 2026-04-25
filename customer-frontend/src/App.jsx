import { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [customers, setCustomers] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const pageSize = 12; // පිටුවකට පේළි 12 බැගින්

  useEffect(() => {
    fetch(`http://localhost:8080/api/customers/paged?page=${page}&size=${pageSize}`)
        .then((res) => res.json())
        .then((data) => {
          setCustomers(data.content);
          setTotalPages(data.totalPages);
        })
        .catch((err) => console.error("API Error:", err));
  }, [page]);

  return (
      <div className="container">
        <header>
          <h1>Customer Directory</h1>
          <p>1,000,000 Records Managed with Pagination</p>
        </header>

        <div className="pagination-bar">
          <button onClick={() => setPage(p => Math.max(0, p - 1))} disabled={page === 0}>
            ◀ Previous
          </button>
          <span className="page-indicator">Page {page + 1} of {totalPages}</span>
          <button onClick={() => setPage(p => p + 1)} disabled={page >= totalPages - 1}>
            Next ▶
          </button>
        </div>

        <div className="table-wrapper">
          <table>
            <thead>
            <tr>
              <th>#</th>
              <th>Customer Name</th>
              <th>NIC Number</th>
              <th>Date of Birth</th>
            </tr>
            </thead>
            <tbody>
            {customers.map((c, index) => (
                <tr key={index}>
                  <td>{page * pageSize + index + 1}</td>
                  <td>{c.name}</td>
                  <td className="nic-cell">{c.nic}</td>
                  <td>{new Date(c.dob).toLocaleDateString()}</td>
                </tr>
            ))}
            </tbody>
          </table>
        </div>
      </div>
  );
}

export default App;