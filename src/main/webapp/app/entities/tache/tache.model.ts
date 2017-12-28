import { BaseEntity } from './../../shared';

export class Tache implements BaseEntity {
    constructor(
        public id?: number,
        public dateEnvoie?: string,
        public status?: string,
        public log?: string,
    ) {
    }
}
