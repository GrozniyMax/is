import {useEffect, useState} from "react";
import {type BatchOperationDto, BatchService} from "../../../../generated/api";

export function BatchOperationTable() {

    const [name, setName] = useState('');
    const [data, setData] = useState<BatchOperationDto[]>([]);

    useEffect(() => {
        BatchService.getFlats(name)
            .then((res) => {
                setData(res)
            })
            .catch((e) => console.log(e));
    }, [name])

    return (
        <div>
            <label>
                <input type="text"
                       value={name}
                       onChange={(e) => setName(e.target.value)}/>
                Введите имя пользователя
            </label>

            {data.length === 0 ? (
                <div>Нет данных</div>
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>Дата создания</th>
                        <th>Успешно</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.map((row, index) => (
                        <tr key={index}> {/* Добавлен key */}
                            <td>{row.creationData}</td>
                            <td>{row.success ? 'Да' : 'Нет'}</td>
                            <td><a href={row.link}>Скачать</a></td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    )
}
