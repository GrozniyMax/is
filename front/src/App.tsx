import './App.css'
import {BrowserRouter, Link, Navigate, Route, Routes} from "react-router-dom";
import {FlatForm} from "./components/forms/Form.tsx";
import {DataTable} from "./components/table/Table.tsx";

function App() {

    return (
        <BrowserRouter>
            <nav>
                <Link to="/update">Обновить</Link>
                <Link to="/create">Создать</Link>
                <Link to="/table">Таблица элементов</Link>
            </nav>

            <Routes>
                <Route path="/" element={<Navigate to="/table" replace={true} />} />
                <Route path="/update" element={<FlatForm type={"update"} initialValues={null} />} />
                <Route path="/create" element={<FlatForm type={"create"} initialValues={null}/>} />
                <Route path="/table" element={<DataTable />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App
