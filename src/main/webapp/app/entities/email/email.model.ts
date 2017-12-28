import { BaseEntity } from './../../shared';

export class Email implements BaseEntity {
    constructor(
        public id?: number,
        public destinataires?: string,
        public expediteur?: string,
        public objet?: string,
        public contenu?: string,
        public pieceJoint?: string,
    ) {
    }
}
