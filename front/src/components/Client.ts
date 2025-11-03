import {OpenAPI} from "../../generated/api";

OpenAPI.BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

