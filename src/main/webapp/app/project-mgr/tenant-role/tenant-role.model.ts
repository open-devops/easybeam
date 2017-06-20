export class TenantRole {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public accountId?: number,
        public policies?: number[],
        public groups?: number[],
        public users?: number[],
    ) {
    }
}
