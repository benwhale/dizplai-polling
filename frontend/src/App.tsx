import React from "react";
import "./App.css";
import Home from "./pages/Home";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={"/logo.svg"} className="App-logo" alt="logo" />
      </header>
      <Home />
    </div>
  );
}

export default App;
