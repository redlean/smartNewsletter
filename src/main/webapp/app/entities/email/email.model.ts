import { BaseEntity } from './../../shared';

export class Email implements BaseEntity {
    constructor(
        public id?: number,
        public objet?: string,
        public contenu?: string,
        public pieceJoint?: string,
        public planifForEmails?: BaseEntity[],
    ) {
    }
}
