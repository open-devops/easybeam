import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { TenantRole } from './tenant-role.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TenantRoleService {

    private resourceUrl = 'api/tenant-roles';

    constructor(private http: Http) { }

    create(tenantRole: TenantRole): Observable<TenantRole> {
        const copy = this.convert(tenantRole);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(tenantRole: TenantRole): Observable<TenantRole> {
        const copy = this.convert(tenantRole);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<TenantRole> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(tenantRole: TenantRole): TenantRole {
        const copy: TenantRole = Object.assign({}, tenantRole);
        return copy;
    }
}
