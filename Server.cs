using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Server
{
    public class Program
    {
        static void Main(string[] args)
        {
            //var ipAddress = Dns.GetHostEntry("localhost").AddressList[0];
            TcpListener server = new TcpListener(IPAddress.Any, 1234);
            server.Start();
            // as long as we're not pending a cancellation, let's keep accepting requests
            TcpClient client = server.AcceptTcpClient();

            StreamReader clientIn = new StreamReader(client.GetStream());

            string msg;
            while ((msg = clientIn.ReadLine()) != null)
            {
                Console.WriteLine(msg);
            }
        }
    }
}
