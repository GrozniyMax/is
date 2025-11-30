import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Header, type HeaderProps} from "./Header.tsx";
import {type FlatDto, FlatService} from "../../../../generated/api";
import "./styles/DataTable.css"

export function DataTable() {
    const [data, setData] = useState<FlatDto[]>([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const [filter, setFilter] = useState("")
    const [page, setPage] = useState(0)
    const [size] = useState(20)
    const [field, setField] = useState("id")
    const [order, setOrder] = useState("asc")

    function buildHeaderParams(name: string): HeaderProps {
        return {
            name: name,
            field: {
                state: field,
                update: setField
            },
            order: {
                state: order,
                update: setOrder
            }
        }
    }

    useEffect(() => {
        setLoading(true);

        FlatService.getFlatsPage(filter, Number(page), Number(size), `${field},${order}`)
            .then((res) => {
                console.log("Received response", res)
                setData(res.content ?? [])
            })
            .catch((err) => {
                console.error(err);
            })

        console.log("Page", page)
        console.log("Size", size)
        console.log("Order", order)
        console.log("Field", field)
        setLoading(false)
    }, [page, size, field, order, filter]); // будет вызываться при изменении этих значений

    return (
        <div>
            {loading && <p>Загрузка...</p>}

            {!loading &&
                <>
                    <h1>Таблица квартир</h1>
                    <label>
                        <input type="text"
                               value={filter}
                               onChange={(e) => setFilter(e.target.value)}/>
                        Фильтрация по имени
                    </label>
                    <table id="data-table">
                        <thead>
                        <tr>
                            <Header {...buildHeaderParams("id")}/>
                            <Header {...buildHeaderParams("name")}/>
                            <Header {...buildHeaderParams("creationDate")}/>
                            <Header {...buildHeaderParams("area")}/>
                            <Header {...buildHeaderParams("price")}/>
                            <Header {...buildHeaderParams("balcony")}/>
                            <Header {...buildHeaderParams("timeToMetroOnFoot")}/>
                            <Header {...buildHeaderParams("numberOfRooms")}/>
                            <Header {...buildHeaderParams("floor")}/>
                            <Header {...buildHeaderParams("centralHeating")}/>
                            <Header {...buildHeaderParams("transport")}/>
                            <Header {...buildHeaderParams("house")}/>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            data.map((row) => (
                                <tr key={row.id}>
                                    <td onClick={() => navigate("/single/update", {state: {flat: row}})}>{row.id}</td>
                                    <td>{row.name}</td>
                                    <td>{row.creationDate}</td>
                                    <td>{row.area}</td>
                                    <td>{row.price}</td>
                                    <td>{String(row.balcony)}</td>
                                    <td>{row.timeToMetroOnFoot}</td>
                                    <td>{row.numberOfRooms}</td>
                                    <td>{row.floor}</td>
                                    <td>{String(row.centralHeating)}</td>
                                    <td>{row.transport}</td>
                                    <td>
                                        <ul>
                                            <li>id - {row.house.id}</li>
                                            <li>id - {row.house.year}</li>
                                            <ul>
                                                <li>first-{`(${row.house.coordinates.first.x}, ${row.house.coordinates.first.y})`}</li>
                                                <li>second-{`(${row.house.coordinates.second.x}, ${row.house.coordinates.second.y})`}</li>
                                            </ul>

                                        </ul>
                                    </td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>

                    <button onClick={() => setPage(page - 1)} disabled={page <= 0}>Предыдущая страница</button>
                    <button onClick={() => setPage(page + 1)}>Следующая страница</button>
                </>}


        </div>
    )
}
