import {useEffect, useState} from "react";
import {getData} from "../../client/Client.ts";
import {useNavigate} from "react-router-dom";
import {Header, type HeaderProps} from "./Header.tsx";
import {TooltipCell} from "./TooltipCell.tsx";
import type {components} from "../../client/dto/types.ts";

type FlatDto = components["schemas"]["FlatDto"]

export function DataTable() {
    const [data, setData] = useState<FlatDto[]>([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();


    const [page, setPage] = useState(0)
    const [size, setSize] = useState(20)
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

        getData(Number(page), Number(size), field, order)
            .then((res) => {
                setData(res);
            })
            .catch((error) => {
                console.error("Ошибка при загрузке данных:", error);
            })
            .finally(() => setLoading(false));
        console.log("Page", page)
        console.log("Size", size)
        console.log("Order", order)
        console.log("Field", field)
    }, [page, size, field, order]); // будет вызываться при изменении этих значений

    return (
        <div>
            {loading && <p>Загрузка...</p>}

            {!loading &&
                <>
                    <h1>Таблица квартир</h1>
                    <table id="data-table">
                        <thead>
                        <tr>
                            <Header {...buildHeaderParams("id")}/>
                            <Header {...buildHeaderParams("name")}/>
                            <Header {...buildHeaderParams("coordinates")}/>
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
                                    <td onClick={() => navigate("/update")}>{row.id}</td>
                                    <td>{row.name}</td>
                                    <td>
                                        <TooltipCell text={row.coordinates.id} tooltip={
                                            <ul className="list-disc pl-5">
                                                <li>X = {row.coordinates.x}</li>
                                                <li>Y = {row.coordinates.y}</li>
                                            </ul>
                                        }/>
                                    </td>
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
                                        <TooltipCell text={row.house.id} tooltip={
                                            <ul className="list-disc pl-5">
                                                <li>Name = {row.house.name}</li>
                                                <li>Year = {row.house.year}</li>
                                                <li>NumberOfFlatsOnFloor = {row.house.numberOfFlatsOnFloor}</li>
                                                <li>NumberOfLifts = {row.house.numberOfLifts}</li>
                                            </ul>
                                        }/>
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
