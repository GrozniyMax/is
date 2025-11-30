import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import {BatchService} from "../../../generated/api";
import {useNavigate} from "react-router-dom";
import "../styles/forms.css";


interface UploadFormValues {
    user: string;
    file: File | null;
}

const validationSchema = Yup.object({
    user: Yup.string()
        .required('Имя пользователя обязательно')
        .min(2, 'Имя пользователя должно содержать минимум 2 символа')
        .max(50, 'Имя пользователя должно содержать максимум 50 символов'),
    file: Yup.mixed<File>()
        .required('Файл обязателен')
        .test('fileType', 'Поддерживаются только JSON файлы', (value) => {
            if (!value) return false;
            return value.type === 'application/json';
        })
        .test('fileSize', 'Файл слишком большой (макс. 10MB)', (value) => {
            if (!value) return false;
            return value.size <= 10 * 1024 * 1024; // 10MB
        })
});

export const BatchOperationForm: React.FC = () => {
    const [isSubmitting, setIsSubmitting] = React.useState(false);
    const [uploadResult, setUploadResult] = React.useState<string>('');
    const navigate = useNavigate();

    const formik = useFormik<UploadFormValues>({
        initialValues: {
            user: '',
            file: null
        },
        validationSchema,
        onSubmit: async (values) => {
            setIsSubmitting(true);
            setUploadResult('');

            try {
                // Создаем объект с файлом для отправки
                const requestBody = {
                    file: values.file!
                };

                await BatchService.postFlatsUpload(values.user, requestBody);

                setUploadResult('success');
                formik.resetForm();
                navigate("/batch/operations")
            } catch (error) {
                console.error('Ошибка загрузки:', error);
                setUploadResult('error');
            } finally {
                setIsSubmitting(false);
            }
        }
    });

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.currentTarget.files?.[0] || null;
        formik.setFieldValue('file', file);
    };

    return (
        <div>
            <h2>Загрузка пакета квартир</h2>
            <p onClick={() => navigate("/batch/operations")}>Результаты операций</p>
            <form onSubmit={formik.handleSubmit}>
                {/* Поле пользователя */}
                <div>
                    <label htmlFor="user">
                        Имя пользователя *
                    </label>
                    <input
                        id="user"
                        name="user"
                        type="text"
                        placeholder="Введите ваше имя"
                        value={formik.values.user}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                    />
                    {formik.touched.user && formik.errors.user && (
                        <div>{formik.errors.user}</div>
                    )}
                </div>

                {/* Поле файла */}
                <div>
                    <label htmlFor="file">
                        JSON файл с данными квартир *
                    </label>
                    <input
                        id="file"
                        name="file"
                        type="file"
                        accept=".json,application/json"
                        onChange={handleFileChange}
                        onBlur={formik.handleBlur}
                    />
                    {formik.touched.file && formik.errors.file && (
                        <div>{formik.errors.file}</div>
                    )}
                    <div>
                        Поддерживаемый формат: JSON (макс. 10MB)
                    </div>
                </div>

                {/* Информация о выбранном файле */}
                {formik.values.file && (
                    <div>
                        <p>
                            Выбран файл: <strong>{formik.values.file.name}</strong>
                        </p>
                        <p>
                            Размер: {(formik.values.file.size / 1024 / 1024).toFixed(2)} MB
                        </p>
                    </div>
                )}

                {/* Кнопка отправки */}
                <button
                    type="submit"
                    disabled={isSubmitting || !formik.isValid}
                >
                    {isSubmitting ? 'Загрузка...' : 'Загрузить файл'}
                </button>

                {/* Результат загрузки */}
                {uploadResult === 'success' && (
                    <div>
                        <p>
                            ✅ Файл успешно загружен и обработан!
                        </p>
                    </div>
                )}

                {uploadResult === 'error' && (
                    <div>
                        <p>
                            ❌ Ошибка при загрузке файла. Попробуйте еще раз.
                        </p>
                    </div>
                )}
            </form>
        </div>
    );
};
