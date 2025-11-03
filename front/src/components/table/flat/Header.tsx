export interface HeaderProps {
    name: string;
    field: {
        state: string;
        update: (value: string) => void;
    }
    order: {
        state: string;
        update: (value: string) => void;
    }
}

export const Header: React.FC<HeaderProps> = (props) => {

    const searchOrder : Record<string, string> = {
        "asc": "desc",
        "desc": "asc",
    }

    function setSort() {
        if (props.field.state !== props.name) {
            const next = searchOrder[props.order.state];

            props.field.update(props.name)
            props.order.update(next)

        }
    }

    return <th onClick={setSort}>{props.name}</th>
}
