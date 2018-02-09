import { BaseEntity } from './../../shared';

export class CompteConfig implements BaseEntity {
    constructor(
        public id?: number,
        public email?: string,
        public password?: string,
    ) {
    }
}
