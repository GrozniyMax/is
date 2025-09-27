import type {components} from "./dto/types.ts";


type FlatDto = components["schemas"]["FlatDto"];

export function getData(
    page: number,
    size: number,
    field: string,
    order: string
): Promise<FlatDto[]> {
    return new Promise((resolve) => {
        setTimeout(() => {
            const mock: FlatDto[] = [
                {
                    id: 1,
                    name: "Квартира на Арбате",
                    coordinates: {
                        id: 101,
                        x: 12.34,
                        y: -100
                    },
                    creationDate: new Date().toISOString(),
                    area: 45.5,
                    price: 5000000,
                    balcony: true,
                    timeToMetroOnFoot: 10.2,
                    numberOfRooms: 2,
                    floor: 3,
                    centralHeating: true,
                    transport: "A_LOT",
                    house: {
                        id: 201,
                        name: "Дом на Арбате",
                        year: 2005,
                        numberOfFlatsOnFloor: 4,
                        numberOfLifts: 2
                    }
                },
                {
                    id: 2,
                    name: "Уютная квартира в центре",
                    coordinates: {
                        id: 102,
                        x: 22.11,
                        y: -120
                    },
                    creationDate: new Date().toISOString(),
                    area: 52.3,
                    price: 6200000,
                    balcony: false,
                    timeToMetroOnFoot: 7.5,
                    numberOfRooms: 3,
                    floor: 5,
                    centralHeating: false,
                    transport: "NORMAL",
                    house: {
                        id: 202,
                        name: "Центральный дом",
                        year: 2010,
                        numberOfFlatsOnFloor: 6,
                        numberOfLifts: 1
                    }
                },
                {
                    id: 3,
                    name: "Квартира у метро",
                    coordinates: {
                        id: 103,
                        x: 30.0,
                        y: -90
                    },
                    creationDate: new Date().toISOString(),
                    area: 60.0,
                    price: 7000000,
                    balcony: true,
                    timeToMetroOnFoot: 2.0,
                    numberOfRooms: 4,
                    floor: 7,
                    centralHeating: true,
                    transport: "FEW",
                    house: {
                        id: 203,
                        name: "Метро Хаус",
                        year: 2018,
                        numberOfFlatsOnFloor: 8,
                        numberOfLifts: 3
                    }
                }
            ];

            resolve(mock);
        }, 500); // можешь изменить задержку
    });
}

