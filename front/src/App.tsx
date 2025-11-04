import './App.css'
import {BrowserRouter, Link, Routes} from "react-router-dom";
import {BatchRoutes, SingleRoutes} from "./components/Routes.tsx";
import {Route} from "react-router-dom";

function App() {

    return (
        <BrowserRouter>
            <nav>
                <Link to="/single/update">Обновить элемент</Link>
                <Link to="/single/create">Создать элемент</Link>
                <Link to="/single/table">Таблица элементов</Link>
                <Link to={"/batch/form"}>Пакетная вставка</Link>
            </nav>


            <Routes>
                <Route path="/batch/*" element={<BatchRoutes />} />
                <Route path="/single/*" element={<SingleRoutes />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App
