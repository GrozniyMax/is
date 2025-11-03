import "./styles/Tooltip.css";

export const TooltipCell: React.FC<{ text: string; tooltip: React.ReactNode }> = ({
                                                                                      text,
                                                                                      tooltip
                                                                                  }) => {
    return (
        <div className="tooltip-wrapper">
            {text}
            <div className="tooltip-box">{tooltip}</div>
        </div>
    );
};
