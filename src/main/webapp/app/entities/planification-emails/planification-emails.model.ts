import { BaseEntity } from './../../shared';

export class PlanificationEmails implements BaseEntity {
    constructor(
        public id?: number,
        public tacheName?: string,
        public expediteur?: string,
        public destinataires?: string,
        public status?: string,
        public datePlanif?: string,
        public planifForEmail?: BaseEntity,
    ) {
    }
}
