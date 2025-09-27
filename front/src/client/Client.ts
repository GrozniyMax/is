import type {components} from "./dto/types.d.ts";

type FlatDto = components["schemas"]["FlatDto"]

export function getData(
    page: number,
    size: number,
    field: string,
    order: string,
    filter: string | null
): Promise<FlatDto[]> {
    const params = new URLSearchParams();

    params.set("page", String(page));
    params.set("size", String(size));
    params.set("sort", `${field},${order}`);

    if (filter) {
        params.set("name", filter);
    }

    return fetch(`/flats/page?${params.toString()}`)
        .then(async (response) => {
            if (!response.ok) {
                throw new Error(`Ошибка при запросе данных: ${response.status}`);
            }
            const json = await response.json();

            // Ожидаем, что сервер возвращает Page<FlatDto>, т.е. объект с полем `content`
            return json.content as FlatDto[];
        });
}

export async function createFlat(flatDto: FlatDto, link: boolean = false): Promise<void> {
    const params = new URLSearchParams();
    params.set("link", String(link));

    const response = await fetch(`/create?${params.toString()}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(flatDto),
    });

    if (!response.ok) {
        throw new Error(`Ошибка при создании квартиры: ${response.status}`);
    }
}

export async function updateFlat(flatDto: FlatDto, link: boolean = false): Promise<void> {
    const params = new URLSearchParams();
    params.set("link", String(link));

    const response = await fetch(`/update?${params.toString()}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(flatDto),
    });

    if (!response.ok) {
        throw new Error(`Ошибка при обновлении квартиры: ${response.status}`);
    }
}
