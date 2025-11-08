import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from "yup";

import {useLocation, useNavigate} from "react-router-dom";
import {useState} from "react";
import {type FlatDto, FlatService, type FlatCreateDto} from "../../../generated/api";
import "../styles/forms.css";

// Enums
const transportOptions = ["LITTLE", "FEW", "NORMAL"] as const;

// 🔍 Валидация
const createValidationSchema = Yup.object().shape({
    name: Yup.string().required("Название обязательно"),
    area: Yup.number()
        .moreThan(0, "Площадь должна быть больше 0")
        .required("Площадь обязательна"),
    price: Yup.number()
        .moreThan(0, "Цена должна быть больше 0")
        .required("Цена обязательна"),
    balcony: Yup.boolean().nullable(),
    timeToMetroOnFoot: Yup.number()
        .moreThan(0, "Время до метро должно быть больше 0")
        .nullable(),
    numberOfRooms: Yup.number()
        .min(1, "Минимум 1 комната")
        .max(20, "Максимум 20 комнат")
        .required("Количество комнат обязательно"),
    floor: Yup.number()
        .moreThan(0, "Этаж должен быть больше 0")
        .required("Этаж обязателен"),
    centralHeating: Yup.boolean().nullable(),
    transport: Yup.mixed<(typeof transportOptions)[number]>()
        .oneOf([...transportOptions])
        .required("Транспорт обязателен"),
    house: Yup.object().shape({
        name: Yup.string().required("Название дома обязательно"),
        year: Yup.number()
            .moreThan(0, "Год постройки должен быть больше 0")
            .required("Год постройки обязателен"),
        numberOfFlatsOnFloor: Yup.number()
            .moreThan(0, "Должно быть больше 0")
            .required("Количество квартир на этаже обязательно"),
        numberOfLifts: Yup.number()
            .moreThan(0, "Должно быть больше 0")
            .required("Количество лифтов обязательно"),
        coordinates: Yup.object().shape({
            first: Yup.object().shape({
                x: Yup.number().required("Координата X первой точки обязательна"),
                y: Yup.number()
                    .moreThan(-166, "Координата Y первой точки должна быть больше -166")
                    .required("Координата Y первой точки обязательна"),
            }),
            second: Yup.object().shape({
                x: Yup.number().required("Координата X второй точки обязательна"),
                y: Yup.number()
                    .moreThan(-166, "Координата Y второй точки должна быть больше -166")
                    .required("Координата Y второй точки обязательна"),
            }),
        }),
    }),
});

const updateValidationSchema = Yup.object().shape({
    id: Yup.number().min(0).required("Id обязателен для обновления"),
    name: Yup.string().required("Название обязательно"),
    area: Yup.number()
        .moreThan(0, "Площадь должна быть больше 0")
        .required("Площадь обязательна"),
    price: Yup.number()
        .moreThan(0, "Цена должна быть больше 0")
        .required("Цена обязательна"),
    balcony: Yup.boolean().nullable(),
    timeToMetroOnFoot: Yup.number()
        .moreThan(0, "Время до метро должно быть больше 0")
        .nullable(),
    numberOfRooms: Yup.number()
        .min(1, "Минимум 1 комната")
        .max(20, "Максимум 20 комнат")
        .required("Количество комнат обязательно"),
    floor: Yup.number()
        .moreThan(0, "Этаж должен быть больше 0")
        .required("Этаж обязателен"),
    centralHeating: Yup.boolean().nullable(),
    transport: Yup.mixed<(typeof transportOptions)[number]>()
        .oneOf([...transportOptions])
        .required("Транспорт обязателен"),
    house: Yup.object().shape({
        name: Yup.string().required("Название дома обязательно"),
        year: Yup.number()
            .moreThan(0, "Год постройки должен быть больше 0")
            .required("Год постройки обязателен"),
        numberOfFlatsOnFloor: Yup.number()
            .moreThan(0, "Должно быть больше 0")
            .required("Количество квартир на этаже обязательно"),
        numberOfLifts: Yup.number()
            .moreThan(0, "Должно быть больше 0")
            .required("Количество лифтов обязательно"),
        coordinates: Yup.object().shape({
            first: Yup.object().shape({
                x: Yup.number().required("Координата X первой точки обязательна"),
                y: Yup.number()
                    .moreThan(-166, "Координата Y первой точки должна быть больше -166")
                    .required("Координата Y первой точки обязательна"),
            }),
            second: Yup.object().shape({
                x: Yup.number().required("Координата X второй точки обязательна"),
                y: Yup.number()
                    .moreThan(-166, "Координата Y второй точки должна быть больше -166")
                    .required("Координата Y второй точки обязательна"),
            }),
        }),
    }),
});

const baseInitial: FlatDto = {
    name: "",
    area: 0,
    price: 0,
    balcony: false,
    timeToMetroOnFoot: 0,
    numberOfRooms: 1,
    floor: 1,
    centralHeating: false,
    transport: "FEW",
    house: {
        name: "",
        year: 2000,
        numberOfFlatsOnFloor: 1,
        numberOfLifts: 1,
        coordinates: {
            first: {
                x: 0,
                y: 0
            },
            second: {
                x: 0,
                y: 0
            }
        }
    },
};

interface FormProps {
    type: string,
    initialValues: FlatDto | null,
}

export const FlatForm: React.FC<FormProps> = ({type, initialValues}) => {

    const navigate = useNavigate();
    const location = useLocation();

    const flat = location.state?.flat;

// Если flat есть — берём его, иначе берём базовые значения
    const initialState = flat || baseInitial;

    const [link, setLink] = useState(false);

    function onSubmit(value: FlatDto) {
        console.log("Форма отправлена!", value);

        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const {id, creationDate, ...rest} = value;
        const creationDto: FlatCreateDto = rest;

        const submitPromise = type === "update"
            ? FlatService.postFlatsUpdate(value, link)
            : FlatService.postFlatsCreate(creationDto, link)

        submitPromise
            .then(() => {
                alert("Успешно сохранено");
                navigate("/single/table");
            })
            .catch(error => {
                console.error("Ошибка:", error);
                alert(`Ошибка при сохранении: ${error.message}`);
            });
    }

    function resolveValidationSchema() {
        if (type === "update") {
            return updateValidationSchema;
        } else {
            return createValidationSchema;
        }
    }


    return (
        <>
            <h1>Форма для операции {type}</h1>
            <Formik initialValues={initialState} validationSchema={resolveValidationSchema}
                    onSubmit={onSubmit} enableReinitialize={true}>
                {() => (
                    <Form>
                        {type === "update" && (
                            <div>
                                <label>ID квартиры</label>
                                <Field name="id" type="number" disabled/>
                                <ErrorMessage name="id" component="div"/>
                            </div>
                        )}

                        <div>
                            <label>Название квартиры</label>
                            <Field name="name"/>
                            <ErrorMessage name="name" component="div"/>
                        </div>

                        <div>
                            <label>Площадь (м²)</label>
                            <Field name="area" type="number"/>
                            <ErrorMessage name="area" component="div"/>
                        </div>

                        <div>
                            <label>Цена (₽)</label>
                            <Field name="price" type="number"/>
                            <ErrorMessage name="price" component="div"/>
                        </div>

                        <div>
                            <label>Балкон</label>
                            <Field name="balcony" type="checkbox"/>
                        </div>

                        <div>
                            <label>Время до метро пешком (мин)</label>
                            <Field name="timeToMetroOnFoot" type="number"/>
                            <ErrorMessage name="timeToMetroOnFoot" component="div"/>
                        </div>

                        <div>
                            <label>Количество комнат</label>
                            <Field name="numberOfRooms" type="number"/>
                            <ErrorMessage name="numberOfRooms" component="div"/>
                        </div>

                        <div>
                            <label>Этаж</label>
                            <Field name="floor" type="number"/>
                            <ErrorMessage name="floor" component="div"/>
                        </div>

                        <div>
                            <label>Центральное отопление</label>
                            <Field name="centralHeating" type="checkbox"/>
                        </div>

                        <div>
                            <label>Транспорт</label>
                            <Field as="select" name="transport">
                                {transportOptions.map((option) => (
                                    <option key={option} value={option}>
                                        {option}
                                    </option>
                                ))}
                            </Field>
                            <ErrorMessage name="transport" component="div"/>
                        </div>

                        <fieldset>
                            <legend>Дом</legend>
                            <div>
                                <label>Название дома</label>
                                <Field name="house.name"/>
                                <ErrorMessage name="house.name" component="div"/>
                            </div>

                            <div>
                                <label>Год постройки</label>
                                <Field name="house.year" type="number"/>
                                <ErrorMessage name="house.year" component="div"/>
                            </div>

                            <div>
                                <label>Квартир на этаже</label>
                                <Field name="house.numberOfFlatsOnFloor" type="number"/>
                                <ErrorMessage name="house.numberOfFlatsOnFloor" component="div"/>
                            </div>

                            <div>
                                <label>Лифтов</label>
                                <Field name="house.numberOfLifts" type="number"/>
                                <ErrorMessage name="house.numberOfLifts" component="div"/>
                            </div>

                            <fieldset>
                                <legend>Координаты дома</legend>

                                <fieldset>
                                    <legend>Первая точка</legend>
                                    <div>
                                        <label>Координата X</label>
                                        <Field name="house.coordinates.first.x" type="number"/>
                                        <ErrorMessage name="house.coordinates.first.x" component="div"/>
                                    </div>

                                    <div>
                                        <label>Координата Y</label>
                                        <Field name="house.coordinates.first.y" type="number"/>
                                        <ErrorMessage name="house.coordinates.first.y" component="div"/>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <legend>Вторая точка</legend>
                                    <div>
                                        <label>Координата X</label>
                                        <Field name="house.coordinates.second.x" type="number"/>
                                        <ErrorMessage name="house.coordinates.second.x" component="div"/>
                                    </div>

                                    <div>
                                        <label>Координата Y</label>
                                        <Field name="house.coordinates.second.y" type="number"/>
                                        <ErrorMessage name="house.coordinates.second.y" component="div"/>
                                    </div>
                                </fieldset>
                            </fieldset>
                        </fieldset>

                        <button type="submit">Сохранить</button>
                    </Form>
                )}
            </Formik>

            <label>
                <input
                    type="checkbox"
                    checked={link}
                    onChange={(e) => setLink(e.target.checked)}
                />
                Связать с существующими объектами
            </label>

            {/* Отладочная информация */}
            <details style={{ marginTop: '20px' }}>
                <summary>Отладочная информация (текущие значения формы)</summary>
                <pre>{JSON.stringify(initialValues ?? baseInitial, null, 2)}</pre>
            </details>
        </>
    );
}
