import { BaseEntity } from './../../shared';

export class Planification_emails implements BaseEntity {
    constructor(
        public id?: number,
        public planifName?: string,
        public destinataire?: string,
        public status?: string,
        public datePlanif?: string,
        public planifForEmail?: BaseEntity,
    ) {
    }
}
