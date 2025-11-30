import "./styles/Tooltip.css";

interface TooltipCellProps {
    data: any; // Объект для отображения в тултипе
    displayField?: string; // Поле для отображения в ячейке (по умолчанию "id")
    tooltipTitle?: string; // Заголовок тултипа
    maxWidth?: string;
}

export const TooltipCell: React.FC<TooltipCellProps> = ({
                                                            data,
                                                            displayField = "id",
                                                            tooltipTitle = "Детальная информация",
                                                            maxWidth = "500px"
                                                        }) => {
    const displayValue = data?.[displayField]?.toString() || "—";

    // Функция для рекурсивного рендеринга объекта
    const renderObject = (obj: any, level = 0): React.ReactNode => {
        if (!obj || typeof obj !== 'object') {
            return <span className="primitive-value">{String(obj)}</span>;
        }

        return (
            <div className={`nested-object level-${level}`}>
                {Object.entries(obj).map(([key, value]) => (
                    <div key={key} className="object-field">
                        <span className="field-name">{key}:</span>
                        {typeof value === 'object' && value !== null ? (
                            renderObject(value, level + 1)
                        ) : (
                            <span className="field-value">{String(value)}</span>
                        )}
                    </div>
                ))}
            </div>
        );
    };

    return (
        <div className="tooltip-wrapper">
            <span className="cell-id">{displayValue}</span>
            <div className="tooltip-box" style={{ maxWidth }}>
                <div className="tooltip-content">
                    <div className="tooltip-header">
                        <h4>{tooltipTitle}</h4>
                    </div>
                    <div className="object-container">
                        {renderObject(data)}
                    </div>
                </div>
            </div>
        </div>
    );
};
