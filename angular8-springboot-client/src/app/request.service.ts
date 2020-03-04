import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private baseUrl = 'http://localhost:8081/springboot-crud-rest/api/v1';

  constructor(private http: HttpClient) { }

  createRequest(request: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/createRequest`, request);
  }

  getRequestList(): Observable<any> {
    return this.http.get(`${this.baseUrl}/getRequestList`);
  }

  updateRequest(id: number, request: Object): Observable<Object> {
    return this.http.put(`${this.baseUrl}/updateRequest/${id}`, request);
  }

  deleteRequest(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/deleteRequest/${id}`, { responseType: 'text' });
  }

  getRequest(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getRequest/${id}`);
  }

}
