import { Armazem } from "./armazem";

export interface ArmazemListResponse {
    content: Armazem[];
    totalElements: number;
}